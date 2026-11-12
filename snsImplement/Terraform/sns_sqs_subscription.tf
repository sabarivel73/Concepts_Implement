resource "aws_sns_topic_subscription" "order_events_to_queue_1" {
  topic_arn = aws_sns_topic.order_events.arn

  protocol = "sqs"

  endpoint = data.aws_sqs_queue.queue_1.arn

  raw_message_delivery = true

  depends_on = [
    aws_sqs_queue_policy.allow_sns_publish
  ]
}
