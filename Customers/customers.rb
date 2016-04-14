#!/usr/bin/env ruby
require_relative 'lib/party_query'
require 'active_support'
require 'json'

unless ARGV.size
  puts "Expected one argument: file with customer data"
  exit
end

begin
  customer_data_file = File.open(ARGV[0])
rescue
  puts "No such file #{ARGV[0]}"
  exit
end

customer_hash = {}
customer_hash[:customers] = []

customer_data = customer_data_file.read.split("\n")
customer_data.each do |customer|
  customer_hash[:customers] << JSON.parse(customer, symbolize_names: true)
end

party_query = PartyQuery.new(customer_hash)
party_query.query_customers(100)

puts party_query.invited_customers
