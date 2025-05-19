variable "aws_access_key" {}
variable "aws_secret_key" {}
provider "aws" {
  region = "ap-southeast-1"
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

resource "aws_s3_bucket" "trongtiniuh-bucket" {
  bucket = "trongtiniuh-bucket"
}

terraform {
  backend "s3" {
    bucket = "stateforcardiotrack"
    key    = "cardiotrack/terraform.tfstate"
    region = "ap-southeast-1" 
    encrypt = true
  }
}
