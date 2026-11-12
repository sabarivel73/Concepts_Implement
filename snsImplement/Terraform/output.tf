output "sns_topic_name" {
  description = "SNS topic name"
  value       = aws_sns_topic.order_events.name
}

output "sns_topic_arn" {
  description = "SNS topic ARN"
  value       = aws_sns_topic.order_events.arn
}

output "sns_topic_id" {
  description = "SNS topic ID"
  value       = aws_sns_topic.order_events.id
}

output "aws_account_id" {
  description = "AWS account ID"
  value       = data.aws_caller_identity.current.account_id
}

output "sqs_queue_arn" {
  description = "Existing SQS queue ARN"
  value       = data.aws_sqs_queue.queue_1.arn
}

output "sqs_queue_url" {
  description = "Existing SQS queue URL"
  value       = data.aws_sqs_queue.queue_1.url
}

output "sns_sqs_subscription_arn" {
  description = "SNS to SQS subscription ARN"
  value       = aws_sns_topic_subscription.order_events_to_queue_1.arn
}
