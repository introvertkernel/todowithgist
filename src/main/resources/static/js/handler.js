var getAllProjects = function () {
    startSpinner();
    fetch('/user/projects/', {
        method: 'GET',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error("Something went wrong", response.error)
        }
        return response.json();
    })
    .then(data => getAllProjects.paint(data))
    .catch(error => {
        console.error(error);
        $('.alert').alert();
    });
};

getAllProjects.paint = function (data) {
    if(data.projectList.length > 0){
        var pDiv = document.querySelector("#projectListContainer");
        var template = document.querySelector("#projectTemplate");
        data.projectList.forEach((p,i) => {
            var clone = template.content.cloneNode(true);
            var content = clone.querySelector("text");
            content.textContent = p.projectName;
            var card = template.querySelector("#projectCards");
            card.setAttribute("id",p.projectId);
            card.setAttribute("row",i);
            pDiv.appendChild(clone);
        });
    }
    stopSpinner();
}

var addNewProject = function () {
    fetch('/user/projects/add', {
        method: 'POST',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(getAddNewProjectRequest())
    })
        .then(response => response.json()
            .then(data => {
                console.log(data);
            }));
};

var getAddNewProjectRequest = function () {
    return {
        projectName: "",
        projectDescription: ""
    }
}

var getAllTodo = function () {
    fetch('/user/projects/todo/', {
        method: 'GET',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => { console.log(data) })
};

var addTodoList = function () {
    fetch('/user/projects/todo/add', {
        method: 'POST',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(getAddTodo())
    })
        .then(response => response.json()
            .then(data => {
                console.log(data);
            }));
};

var getAddTodo = function () {
    return {};
};


// spinner
var startSpinner = function(){
    document.querySelector(".spanner").className+=" show";
    document.querySelector(".overlay").className+=" show";
};
var stopSpinner = function(){
    document.querySelector(".spanner").className="spanner";
    document.querySelector(".overlay").className+="overlay";
}