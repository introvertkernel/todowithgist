var pid, pname;

var getAllProjects = function () {
  startSpinner();
  fetch("/user/projects/", {
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
      $(".alert").alert();
    });
};

getAllProjects.paint = function (data) {
  console.log(data);
  if (data.projectList.length > 0) {
    var pDiv = document.querySelector("#projectListContainer");
    var template = document.querySelector("#projectTemplate");
    data.projectList.forEach((p, i) => {
      var clone = template.content.cloneNode(true);
      var content = clone.querySelector("text");
      content.textContent = p.projectName;
      var card = clone.querySelectorAll(".projectCards");
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
	modal.find(".modal-title").attr("projectId", "");
    modal.find(".modal-title").text("New Project");
	document.querySelector("#listul").innerHTML = "";
    toggleModal();
  }
};

var addNewProject = function () {
  fetch("/user/projects/add", {
    method: "POST",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(getAddNewProjectRequest()),
  }).then((response) =>
    response.json().then((data) => {
      console.log(data);
    })
  );
};

var getAddNewProjectRequest = function () {
  return {
    projectName: "",
    projectDescription: "",
  };
};

var getAllTodo = function () {
  fetch("/user/projects/todo?projectId=" + pid, {
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
    .then((data) => getAllTodo.paint(data));
};

getAllTodo.paint = function (data) {
  console.log(data);
  if (data.TodoList.length > 0) {
    var pDiv = document.querySelector("#listul");
    pDiv.innerHTML = "";
    var template = document.querySelector("#todo-template");
    data.TodoList.forEach((p, i) => {
      var clone = template.content.cloneNode(true);
      clone.querySelector("div").setAttribute("todoId", p.todoId);
      clone.querySelector("#task-name").textContent = p.todoDesc;
      clone.querySelector("input").checked = p.todoStatus === "C";
      pDiv.appendChild(clone);
    });
    toggleModal();
  }
  stopSpinner();
};

var addNewTodo = function () {
  var name = $("#add-new-input").val();
  var pDiv = document.querySelector("#listul");
  var template = document.querySelector("#todo-template");
  var clone = template.content.cloneNode(true);
  clone.querySelector("#task-name").textContent = name;
  clone.querySelector("input").checked = false;
  pDiv.appendChild(clone);
};

var deleteTodo = function (pthis) {
  debugger;
  var todoId = $($(pthis).parent()).attr("todoid");
  fetch("/user/projects/todo?todoId=" + todoId, {
    method: "DELETE",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        console.log(response);
        throw new Error("Something went wrong", response.error);
      } else {
        $(pthis).parent().remove();
      }
    })
    .then((data) => getAllTodo());
};
var addOrUpdateTodoList = function () {
  fetch("/user/projects/todo/add", {
    method: "POST",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(getAddTodo()),
  }).then((response) =>
    response.json().then((data) => {
      console.log(data);
    })
  );
};

var getAddTodo = function () {
  $("#listul li").map(function (p) {
    debugger;
    return {
      todoId: "",
    };
  });
  return {};
};

var toggleModal = function () {
  $("#main-modal").modal("toggle");
};

// spinner
var startSpinner = function () {
  document.querySelector(".spanner").className += " show";
  document.querySelector(".overlay").className += " show";
};
var stopSpinner = function () {
  document.querySelector(".spanner").className = "spanner";
  document.querySelector(".overlay").className += "overlay";
};
