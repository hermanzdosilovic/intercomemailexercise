require 'great_circle_dist'

RSpec.describe GreatCircleDist, "#distance" do
  it 'calculates distance' do
    distance = GreatCircleDist.distance(50, 0, 60, 0)
    expect(distance.floor).to eq 1111
  end
end
