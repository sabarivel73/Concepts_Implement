output "main_queue_name" {
  value = aws_sqs_queue.main.name
}

output "main_queue_url" {
  value = aws_sqs_queue.main.id
}

output "main_queue_arn" {
  value = aws_sqs_queue.main.arn
}

output "dlq_queue_name" {
  value = aws_sqs_queue.dlq.name
}

output "dlq_queue_url" {
  value = aws_sqs_queue.dlq.id
}

output "dlq_queue_arn" {
  value = aws_sqs_queue.dlq.arn
}