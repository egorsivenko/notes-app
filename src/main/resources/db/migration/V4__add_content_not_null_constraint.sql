UPDATE notes
SET content = ''
WHERE content IS NULL;

ALTER TABLE notes
    ALTER COLUMN content SET NOT NULL;