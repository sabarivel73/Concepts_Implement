variable "queue_name" {
  description = "Name of the main SQS queue"
  type        = string
}

variable "environment" {
  description = "Deployment environment"
  type        = string

  default = "dev"
}