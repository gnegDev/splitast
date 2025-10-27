import requests
from flask import Blueprint, render_template, request, make_response, redirect

from config import API_HOST

dashboard_controller = Blueprint("dashboard_controller", __name__, template_folder="../static")
@dashboard_controller.route("/dashboard", methods=["GET"])
def render_main_page():
    user_id = request.cookies.get('user_id')

    if not user_id:
        redirect_response = make_response(redirect("/auth"))
        return redirect_response

    user = requests.get(API_HOST + f"/splitast/api/user/{user_id}")
    tasks = list()

    for task in user.json()["tasks"]:
        tasks.append({
            "id": task["id"],
            "status": task["status"],
            "severity": task["severity"],
            "message": task["message"],
            "uri": task["uri"],
            "line": task["line"]
        })

    return render_template("dashboard_template.html", tasks=tasks)