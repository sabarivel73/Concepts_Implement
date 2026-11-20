provider "aws" {
  region = "ap-south-1"
}

resource "aws_ses_email_identity" "sender" {
  email = "mailAddress"
}
