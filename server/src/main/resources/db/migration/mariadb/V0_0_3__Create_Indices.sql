CREATE INDEX IDX_ZIP_LONG_LAT_PROC
	ON geo_location (longitude, latitude, processed, zip);
CREATE INDEX IDX_LONG_LAT_PROC
	ON geo_location (longitude, latitude, processed);