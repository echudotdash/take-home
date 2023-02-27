import pytest
import requests
import jsonschema
from jsonschema import validate

# Define the schema for the response JSON message
schema = {
    "type": "object",
    "properties": {
        "id": {"type": "number"},
        "text": {"type": "string"}
    },
    "required": ["id", "text"]
}

# values can be defined in a public file
URL = "http://localhost:8080/game"
NAMES = ["Chess"]


# put needed test cases in a class
class TestAPI(object):

    def __check_json_format(self, json_c):
        try:
            validate(instance=json_c, schema=schema)
        except jsonschema.exceptions.ValidationError as e:
            assert False, e

    @pytest.mark.parametrize("name", NAMES)
    def test_get_game_with_name(self, name):
        url = URL + "?name=" + name
        response = requests.get(url)

        # check response status code 200
        assert response.status_code == 200

        # check response JSON format
        json_response = response.json()
        self.__check_json_format(json_response)

    def test_get_game_without_name(self):
        response = requests.get(URL)

        # check response status code 200
        assert response.status_code == 200

        # check response JSON format
        json_response = response.json()
        self.__check_json_format(json_response)
