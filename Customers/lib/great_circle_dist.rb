module GreatCircleDist
  EARTH_RADIUS = 6371

  def self.distance(lat1, long1, lat2, long2)
    lat1 = lat1*Math::PI/180
    lat2 = lat2*Math::PI/180
    long1 = long1*Math::PI/180
    long2 = long2*Math::PI/180

    delta_lat = (lat1 - lat2).abs
    delta_long = (long1 - long2).abs

    angle = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(delta_long))

    EARTH_RADIUS*angle
  end
end
