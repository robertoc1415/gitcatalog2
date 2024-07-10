db = db.getSiblingDB('catalog');

db.createUser(
    {
        user: "catalog",
        pwd: "password",
        roles: [
            {
                role: "readWrite",
                db: "catalog"
            }
        ]
    }
);