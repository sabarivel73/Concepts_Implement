variable "aws_region" {
  description = "AWS region where the SNS topic will be created"
  type        = string
  default     = "ap-south-1"
}

variable "sns_topic_name" {
  description = "Name of the SNS Standard topic"
  type        = string
  default     = "order-events"
}

variable "sns_display_name" {
  description = "SNS display name"
  type        = string
  default     = "Order Events"
}

variable "enable_sns_encryption" {
  description = "Enable SNS server-side encryption"
  type        = bool
  default     = true
}

variable "sns_kms_key_id" {
  description = "Optional customer-managed KMS key ID/ARN/alias"
  type        = string
  default     = null
}

variable "sns_tracing_config" {
  description = "SNS tracing configuration"
  type        = string
  default     = "PassThrough"

  validation {
    condition = contains(
      ["PassThrough", "Active"],
      var.sns_tracing_config
    )

    error_message = "Value must be PassThrough or Active."
  }
}