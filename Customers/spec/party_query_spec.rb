require 'party_query'
require 'pry'

RSpec.describe PartyQuery, "#query_customers" do
  before :each do
    @customers = {
      customers: [
        {"latitude": "53.2451022", "user_id": 4, "name": "Ian Kehoe", "longitude": "-6.238335"},
        {"latitude": "55.033", "user_id": 19, "name": "Enid Cahill", "longitude": "-8.112"},
        {"latitude": "53.008769", "user_id": 11, "name": "Richard Finnegan", "longitude": "-6.1056711"}
      ]
    }

    @expected_invited_customers = {
      customers: [
        {"user_id": 4, "name": "Ian Kehoe"},
        {"user_id": 11, "name": "Richard Finnegan"}
      ]
    }
  end

  it 'invites one customer' do
    party_query = PartyQuery.new(@customers)
    party_query.query_customers(100)
    expect(party_query.invited_customers.size).to eq 1
  end

  it 'sorts by user id' do
    party_query = PartyQuery.new(@customers)
    party_query.query_customers(100)
    expect(party_query.invited_customers).to eq @expected_invited_customers
  end
end
