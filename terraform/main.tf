
provider "aws" {
  region = "ap-southeast-1"
  
}

resource "aws_s3_bucket" "trongtiniuh-bucket" {
  bucket = "trongtiniuh-bucket"
}

