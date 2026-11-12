data "aws_iam_policy_document" "allow_sns_to_sqs" {
  statement {
    sid    = "AllowSNSPublishToQueue1"
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["sns.amazonaws.com"]
    }

    actions = [
      "sqs:SendMessage"
    ]

    resources = [
      data.aws_sqs_queue.queue_1.arn
    ]

    condition {
      test     = "ArnEquals"
      variable = "aws:SourceArn"

      values = local.sns_topic_arns
    }
  }
}

resource "aws_sqs_queue_policy" "allow_sns_publish" {
  queue_url = data.aws_sqs_queue.queue_1.url

  policy = data.aws_iam_policy_document.allow_sns_to_sqs.json
}
