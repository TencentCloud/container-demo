/**
 * Creates pre-filled demo account
 * 初始化命令：mongo -u mongouser -p --authenticationDatabase "admin" [mongodb的IP]/piggymetrics mongo-init.js
 */

print('init start');

// 创建账号 guest 密码也是 guest
if (!db.getUser('guest')) { db.createUser({ user: "guest", pwd: "guest", roles: [{ role: "readWrite", db: "piggymetrics" }], mechanisms: ["SCRAM-SHA-1"] }) }

print('dump default demo account start');

db.accounts.update(
    { "_id": "demo" },
    {
    "_id": "demo",
    "lastSeen": new Date(),
    "note": "demo note",
    "expenses": [
        {
            "amount": 1300,
            "currency": "USD",
            "icon": "home",
            "period": "MONTH",
            "title": "Rent"
        },
        {
            "amount": 120,
            "currency": "USD",
            "icon": "utilities",
            "period": "MONTH",
            "title": "Utilities"
        },
        {
            "amount": 20,
            "currency": "USD",
            "icon": "meal",
            "period": "DAY",
            "title": "Meal"
        },
        {
            "amount": 240,
            "currency": "USD",
            "icon": "gas",
            "period": "MONTH",
            "title": "Gas"
        },
        {
            "amount": 3500,
            "currency": "EUR",
            "icon": "island",
            "period": "YEAR",
            "title": "Vacation"
        },
        {
            "amount": 30,
            "currency": "EUR",
            "icon": "phone",
            "period": "MONTH",
            "title": "Phone"
        },
        {
            "amount": 700,
            "currency": "USD",
            "icon": "sport",
            "period": "YEAR",
            "title": "Gym"
        }
    ],
    "incomes": [
        {
            "amount": 42000,
            "currency": "USD",
            "icon": "wallet",
            "period": "YEAR",
            "title": "Salary"
        },
        {
            "amount": 500,
            "currency": "USD",
            "icon": "edu",
            "period": "MONTH",
            "title": "Scholarship"
        }
    ],
    "saving": {
            "amount": 5900,
            "capitalization": false,
            "currency": "USD",
            "deposit": true,
            "interest": 3.32
        }
    },
    { upsert: true }
);

print('dump complete');