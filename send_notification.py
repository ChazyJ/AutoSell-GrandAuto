import requests
import json

def send_notification():
    url = "https://fcm.googleapis.com/fcm/send"
    headers = {
        "Content-Type": "application/json",
        "Authorization": "key=YOUR_SERVER_KEY"  # Замените YOUR_SERVER_KEY на ваш ключ сервера
    }
    data = {
        "to": "/topics/all",
        "notification": {
            "title": "Новое объявление",
            "body": "Добавлен новый автомобиль. Проверьте его в приложении!"
        }
    }
    response = requests.post(url, headers=headers, data=json.dumps(data))
    print(response.status_code, response.text)

if __name__ == "__main__":
    send_notification()
