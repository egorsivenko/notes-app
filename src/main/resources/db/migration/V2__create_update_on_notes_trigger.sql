CREATE OR REPLACE FUNCTION update_last_updated_on_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.last_updated_on = current_timestamp;
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER update_last_updated_on_timestamp
    BEFORE UPDATE
    ON notes
    FOR EACH ROW
EXECUTE PROCEDURE update_last_updated_on_column();