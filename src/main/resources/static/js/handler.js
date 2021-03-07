var pid, pname;

var getAllProjects = function () {
  startSpinner();
  fetch("/api/user/projects/", {
    method: "GET",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Something went wrong", response.error);
      }
      return response.json();
    })
    .then((data) => getAllProjects.paint(data))
    .catch((error) => {
      console.error(error);
      alertsHandler();
    });
};

getAllProjects.paint = function (data) {
  console.log(data);
  $(".projectCards").remove();
  if (data.length > 0) {
    var pDiv = document.querySelector("#projectListContainer");
    var template = document.querySelector("#projectTemplate");
    data.forEach((p, i) => {
      var clone = template.content.cloneNode(true);
      var content = clone.querySelector("text");
      content.textContent = p.projectName;
      var card = clone.querySelectorAll(".projectCards");
      card.item(0).setAttribute("pid", p.projectId);
      card.item(0).setAttribute("id", p.projectId);
      card.item(0).setAttribute("row", i);
      pDiv.appendChild(clone);
    });
  }
  stopSpinner();
};

var projectCardClickHandler = function (pthis) {
  if (pthis !== undefined) {
    pid = pthis.id;
    pname = $(pthis).find("text").text();
    var modal = $("#main-modal");
    modal.find(".modal-title").attr("projectId", pid);
    modal.find(".modal-title").text(pname);
    getAllTodo();
  } else {
    pid = "";
    pname = "";
    var modal = $("#main-modal");
    modal.find(".modal-title").attr("projectId", "");
    modal.find(".modal-title").text("New Project");
    document.querySelector("#listul").innerHTML = "";
    toggleModal();
  }
};

var addNewProject = function () {
  startSpinner();
  fetch("/api/user/projects/add", {
    method: "POST",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(getAddNewProjectRequest()),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      stopSpinner();
    })
    .catch((error) => {
      console.error(error);
      alertsHandler();
      stopSpinner();
    });
};

var getAddNewProjectRequest = function () {
  return {
    projectName: "",
    projectDescription: "",
  };
};

var getAllTodo = function () {
  startSpinner();
  fetch("/api/user/projects/todo?projectId=" + pid, {
    method: "GET",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Something went wrong", response.error);
      }
      return response.json();
    })
    .then((data) => getAllTodo.paint(data))
    .catch(error => {
      console.log(error);
      alertsHandler();
    });
  stopSpinner();
};

getAllTodo.paint = function (data) {
  console.log(data);
  if (data.length > 0) {
    var pDiv = document.querySelector("#listul");
    pDiv.innerHTML = "";
    var template = document.querySelector("#todo-template");
    data.forEach((p, i) => {
      var clone = template.content.cloneNode(true);
      clone.querySelector("tr").setAttribute("todoId", p.todoId);
      clone.querySelector("#task-name").textContent = p.todoDesc;
      clone.querySelector("input").checked = p.todoStatus === "C";
      clone.querySelector("#date").textContent = p.createTs !== undefined ? p.createTs : "-";
      pDiv.appendChild(clone);
    });
  }
  toggleModal();
  stopSpinner();
};

var addNewTodo = function () {
  var name = $("#add-new-input").val();
  $("#add-new-input").val("");
  var pDiv = document.querySelector("#listul");
  var template = document.querySelector("#todo-template");
  var clone = template.content.cloneNode(true);
  clone.querySelector("#task-name").textContent = name;
  clone.querySelector("input").checked = false;
  pDiv.appendChild(clone);
};

var deleteTodo = function (pthis) {
  debugger;
  var todoId = $(pthis).parents().eq(1).attr("todoid");
  fetch("/api/user/projects/todo?todoId=" + todoId, {
    method: "DELETE",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      toggleModal();
      if (!response.ok) {
        console.log(response);
        throw new Error("Something went wrong", response.error);
      } else {
        $(pthis).parents().eq(1).remove();
      }
    })
    .then((data) => {
      getAllTodo();
      alertsHandler("alert-success", "Deleted")
    })
    .catch(error => {
      console.error(error);
      alertsHandler();
    });
};
var addOrUpdateTodoList = function () {
  startSpinner();
  fetch("/api/user/projects/add", {
    method: "POST",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(getAddTodo()),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Something went wrong", response.error);
      }
      return response.json();
    })
    .then((data) => {
      toggleModal();
      console.log(data);
      getAllProjects();
      alertsHandler("alert-success", "Saved")
      stopSpinner();
    })
    .catch((error) => {
      console.error(error);
      alertsHandler();
      getAllProjects();
      stopSpinner();
    });
};

var getAddTodo = function () {
  var todoObj = $.map($("#listul tr"), function (val) {
    return {
      todoId: $(val).attr("todoId"),
      todoDesc: $(val).find("#task-name").text(),
      // todoDate: $(val).find("#date").text() === "-" ? "" : $(val).find("#date").text(),
      todoStatus: $($(val).find("input")[0]).prop("checked") ? "C" : "P",
    };
  });
  var finalObj = {
    projectId: pid,
    projectName: $("#modal-title").text(),
    todoPayload: todoObj,
  };
  console.log(finalObj);
  return finalObj;
};

var exportToGist = function (pthis) {
  startSpinner();
  event.stopPropagation();
  var tempId = $(pthis).parents().eq(4).attr("id");
  fetch("/api/user/gist?projectId=" + tempId, {
    method: "GET",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Something went wrong", response.error);
      }
      return response.json();
    })
    .then((data) => {
      console.log(data);
      alertsHandler("alert-success", "Successfully exported to Gist")
      stopSpinner();
    })
    .catch((error) => {
      console.error(error);
      alertsHandler();
      stopSpinner();
    });
  getAllProjects();
};
var deleteProject = function (pthis) {
  startSpinner();
  event.stopPropagation();
  var tempId = $(pthis).parents().eq(4).attr("id");
  if(tempId !== undefined){
    fetch("/api/user/projects?projectId=" + tempId, {
      method: "DELETE",
      credentials: "same-origin",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Something went wrong", response.error);
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
        alertsHandler("alert-success", "Project deleted");
        getAllProjects();
        stopSpinner();
      })
      .catch((error) => {
        console.error(error);
        alertsHandler();
        getAllProjects();
        stopSpinner();
      });
  } else {
    stopSpinner();
    $(pthis).parents().eq(1).remove();
  }
};
var toggleModal = function () {
  $("#main-modal").modal("toggle");
};

var alertsHandler = function (type, msg) {
  var pDiv = document.querySelector("#alert-div");
  var template = document.querySelector("#alert-template");
  var clone = template.content.cloneNode(true);
  pDiv.appendChild(clone);
  if (msg !== undefined) {
    $("#alert")[0].innerHTML = msg;
  } else {
    $("#alert")[0].innerHTML =
      "<strong>Error!</strong> Unable to process your request. Kindly try after sometime.";
  }
  $("#alert").removeClass();
  $("#alert").addClass("alert");
  if (type !== undefined) {
    $("#alert").addClass(type);
  } else {
    $("#alert").addClass("alert-danger");
  }
  setTimeout(function () {
    $("#alert").alert("close");
  }, 5000);
};

// spinner
var startSpinner = function () {
  document.querySelector("#spanner").className += "spanner show";
  document.querySelector("#overlay").className += "overlay show";
  $("body").addClass("no-scroll")
};
var stopSpinner = function () {
  document.querySelector("#spanner").className = "";
  document.querySelector("#overlay").className = "";
  $("body").removeClass("no-scroll")
};
