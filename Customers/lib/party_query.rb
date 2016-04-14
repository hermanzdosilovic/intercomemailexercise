require_relative 'great_circle_dist'
require 'active_support'
require 'pry'

class PartyQuery
  attr_reader :customers, :invited_customers

  DUBLIN_LAT = 53.3381985
  DUBLIN_LONG = -6.2592576

  def initialize(customers)
    @customers = customers
  end

  def query_customers(max_distance)
    @invited_customers = {}
    invited_customers[:customers] = []
    customers[:customers].each do |customer|
      # binding.pry
      lat = customer[:latitude].to_f
      long = customer[:longitude].to_f
      distance = GreatCircleDist.distance(DUBLIN_LAT, DUBLIN_LONG, lat, long)
      invited_customers[:customers] << customer.slice(:user_id, :name) if distance <= max_distance
    end

    invited_customers[:customers].sort!{ |a, b| a[:user_id] <=> b[:user_id] }
  end
end
