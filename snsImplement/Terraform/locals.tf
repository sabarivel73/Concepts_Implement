locals {
  common_tags = {
    Project     = "sns-learning"
    Environment = "dev"
    ManagedBy   = "Terraform"
    Component   = "messaging"
  }
}

locals {
  sns_topic_arns = [
    aws_sns_topic.order_events.arn,
  ]
}
