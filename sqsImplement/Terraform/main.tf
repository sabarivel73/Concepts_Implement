provider "aws" {
  region = "ap-south-1"
}

resource "aws_sqs_queue" "main" {
  name = var.queue_name
  visibility_timeout_seconds = 60
  message_retention_seconds = 345600
  delay_seconds = 0
  max_message_size = 262144
  receive_wait_time_seconds = 10
  sqs_managed_sse_enabled = true
  tags = {
    Name        = var.queue_name
    Environment = var.environment
  }
}

resource "aws_sqs_queue" "dlq" {
  name = "${var.queue_name}-dlq"
  visibility_timeout_seconds = 60
  message_retention_seconds = 1209600
  delay_seconds = 0
  max_message_size = 262144
  receive_wait_time_seconds = 10
  sqs_managed_sse_enabled = true
  tags = {
    Name        = "${var.queue_name}-dlq"
    Environment = var.environment
    Type        = "DLQ"
  }
}

resource "aws_sqs_queue_redrive_policy" "main" {
  queue_url = aws_sqs_queue.main.id
  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.dlq.arn
    maxReceiveCount = 3
  })
}

resource "aws_sqs_queue_redrive_allow_policy" "dlq" {
  queue_url = aws_sqs_queue.dlq.id
  redrive_allow_policy = jsonencode({
    redrivePermission = "byQueue"
    sourceQueueArns = [
      aws_sqs_queue.main.arn
    ]
  })
}
