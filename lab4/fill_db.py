import urllib3, json, jaydebeapi, time, datetime
from Crypto.Hash import SHA256

USER_COUNT = 250
IGNORED_KEYS = [
    "id",
    "uid",
    "avatar",
    "gender",
    "phone_number",
    "social_insurance_number",
    "employment",
    "address",
    "credit_card",
    "subscription",
    "date_of_birth",
]


def fetch():
    http = urllib3.PoolManager()
    users = []
    for i in range(0, USER_COUNT):
        user = json.loads(
            http.request(
                "GET", "https://random-data-api.com/api/users/random_user"
            ).data
        )

        for key in IGNORED_KEYS:
            user.pop(key, None)

        users.append(user)
        print(f"Fetching random users from API [{i}/{USER_COUNT}]", end="\r")
    print("\nFetch Done!")

    with open("users.json", "w+") as file:
        file.write(json.dumps(users))


def fill_db():

    users = []
    with open("users.json", "r") as file:
        users = [
            (
                user["username"],
                user["email"],
                user["first_name"],
                user["last_name"],
                SHA256.new(user["password"].encode()).hexdigest(),
            )
            for user in json.loads(file.read())
        ]
        conn = jaydebeapi.connect(
            "org.h2.Driver",
            "jdbc:h2:file:./database",
            ["db", "password"],
            "./h2-2.1.212.jar",
        )

        conn.cursor().executemany(
            "INSERT INTO Users (Username, Email, First_Name, Last_Name, Password_Hash) VALUES (?, ?, ?, ?, ?)",
            users,
        )


if __name__ == "__main__":
    # fetch()
    fill_db()
