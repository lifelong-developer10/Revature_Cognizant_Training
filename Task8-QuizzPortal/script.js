let currentQ = 0;
let userAnswers = Array(questions.length).fill(null);
let timer;
let timeLeft = 60;

function startTimer(){
    timeLeft = 60;
    $("#timer").text("Time: 01:00");
    clearInterval(timer);
    timer = setInterval(() => {
        timeLeft--;
        let min = String(Math.floor(timeLeft/60)).padStart(2,'0');
        let sec = String(timeLeft%60).padStart(2,'0');
        $("#timer").text(`Time: ${min}:${sec}`);
        if(timeLeft <= 0){
            clearInterval(timer);
            nextQuestion();
        }
    },1000);
}

function renderSidebar(){
    $("#questionSidebar").html("");
    questions.forEach((q,i)=>{
        let cls = userAnswers[i] ? "answered" : "unanswered";
        if(i===currentQ) cls = "current";
        $("#questionSidebar").append(`<button class="${cls}" data-index="${i}">${i+1}</button>`);
    });
    updateMerit();
}

function renderQuestion(){
    const q = questions[currentQ];
    let html = `<h5>${currentQ+1}. ${q.question}</h5>`;
    q.options.forEach(opt=>{
        const selected = userAnswers[currentQ]===opt ? "selected" : "";
        html += `<button class="option-btn ${selected}" data-opt="${opt}">${opt}</button>`;
    });
    $("#quizQuestion").html(html);
    startTimer();
    renderSidebar();
}

function saveAnswer(ans){
    if(ans) userAnswers[currentQ] = ans;
    renderSidebar();
}

function nextQuestion(){
    currentQ = Math.min(currentQ+1, questions.length-1);
    renderQuestion();
}

function prevQuestion(){
    currentQ = Math.max(currentQ-1,0);
    renderQuestion();
}

function updateMerit(){
    const completed = userAnswers.filter(a=>a!==null).length;
    const percent = (completed/questions.length)*100;
    $("#meritBar").css("width", percent+"%");
    $("#meritText").text(`${completed}/${questions.length} Completed`);
}


$(document).on("click","#questionSidebar button",function(){
    currentQ = $(this).data("index");
    renderQuestion();
});


$(document).on("click",".option-btn",function(){
    $(".option-btn").removeClass("selected");
    $(this).addClass("selected");
    saveAnswer($(this).data("opt"));
});

$("#nextBtn").click(nextQuestion);
$("#prevBtn").click(prevQuestion);


$("#submitBtn").click(()=>{
    clearInterval(timer);
    let score = 0;
    let html = "<h4>Results</h4>";
    questions.forEach((q,i)=>{
        if(userAnswers[i]===q.answer) score++;
        else html += `<p>Q${i+1}: Correct Answer - ${q.answer}</p>`;
    });
    html = `<h4>Your Score: ${score}/${questions.length}</h4>` + html;
    $("#quizQuestion").html(html);
    $("#prevBtn,#nextBtn,#submitBtn,#questionSidebar").hide();
});

renderQuestion();
