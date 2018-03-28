ALTER TABLE geo_location ADD COLUMN processed datetime(6);


UPDATE geo_location SET processed = CURRENT_DATE()
WHERE longitude IS NOT NULL AND latitude IS NOT NULL;