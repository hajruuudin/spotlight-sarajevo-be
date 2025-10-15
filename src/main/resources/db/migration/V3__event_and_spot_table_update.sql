ALTER TABLE ss_event
ADD COLUMN event_lat NUMERIC NULL,
ADD COLUMN event_long NUMERIC NULL;

ALTER TABLE ss_spot
RENAME COLUMN spot_contact_phopne to spot_contact_phone;