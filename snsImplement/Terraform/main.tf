data "aws_caller_identity" "current" {}

resource "aws_sns_topic" "order_events" {
  name = var.sns_topic_name

  display_name = var.sns_display_name

  tracing_config = var.sns_tracing_config

  signature_version = "2"

  kms_master_key_id = var.enable_sns_encryption ? (
    var.sns_kms_key_id != null
    ? var.sns_kms_key_id
    : "alias/aws/sns"
  ) : null

  delivery_policy = jsonencode({
    http = {
      defaultHealthyRetryPolicy = {
        minDelayTarget     = 20
        maxDelayTarget     = 20
        numRetries         = 3
        numMinDelayRetries = 0
        numMaxDelayRetries = 3
        numNoDelayRetries  = 0
        backoffFunction    = "linear"
      }

      disableSubscriptionOverrides = false

      defaultThrottlePolicy = {
        maxReceivesPerSecond = 10
      }
    }
  })

  tags = local.common_tags
}

data "aws_iam_policy_document" "sns_topic_policy" {
  statement {
    sid    = "AllowAccountOwnerSNSAccess"
    effect = "Allow"

    principals {
      type        = "AWS"
      identifiers = [
        "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
      ]
    }

    actions = [
      "sns:GetTopicAttributes",
      "sns:SetTopicAttributes",
      "sns:AddPermission",
      "sns:RemovePermission",
      "sns:DeleteTopic",
      "sns:Subscribe",
      "sns:ListSubscriptionsByTopic",
      "sns:Publish",
      "sns:ListTagsForResource"
    ]

    resources = [
      aws_sns_topic.order_events.arn
    ]
  }
}

resource "aws_sns_topic_policy" "order_events" {
  arn = aws_sns_topic.order_events.arn

  policy = data.aws_iam_policy_document.sns_topic_policy.json
}