data "aws_sqs_queue" "queue_1" {
  name = "order-queue"
}

data "aws_sqs_queue" "queue_1-dlq" {
  name = "order-queue-dlq"
}
