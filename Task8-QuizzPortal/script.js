let currentQ = 0;
let userAnswers = Array(questions.length).fill(null);
let timer;
let timeLeft = 60;

// ===== Start Timer =====
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

// ===== Render Sidebar =====
let visitedQuestions = [];

function renderSidebar(){
    $("#questionSidebar").html("");
    questions.forEach((q,i)=>{
        let cls = "unanswered";
        if(userAnswers[i]) cls = "answered";
        else if(i === currentQ) cls = "current";
        else if(visitedQuestions.includes(i)) cls = "visited";

        $("#questionSidebar").append(
            `<button class="${cls}" data-index="${i}" data-question="${q.question}">${i+1}</button>`
        );
    });
    updateMerit();
}

// Save visited questions
function saveAnswer(ans){
    if(ans) userAnswers[currentQ] = ans;
    if(!visitedQuestions.includes(currentQ)) visitedQuestions.push(currentQ);
    renderSidebar();
}


// ===== Render Question =====
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

// ===== Save Answer =====
function saveAnswer(ans){
    if(ans) userAnswers[currentQ] = ans;
    renderSidebar();
}

// ===== Navigation =====
function nextQuestion(){
    currentQ = Math.min(currentQ+1, questions.length-1);
    renderQuestion();
}

function prevQuestion(){
    currentQ = Math.max(currentQ-1,0);
    renderQuestion();
}

// ===== Update Progress =====
function updateMerit(){
    const completed = userAnswers.filter(a=>a!==null).length;
    const percent = (completed/questions.length)*100;
    $("#meritBar").css("width", percent+"%");
    $("#meritText").text(`${completed}/${questions.length} Completed`);
}

// ===== Click Events =====
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

// ===== Submit Quiz & Show Congratulations =====
$("#submitBtn").click(() => {
    clearInterval(timer);
    let score = 0;

    questions.forEach((q, i) => {
        if (userAnswers[i] === q.answer) score++;
    });

    const total = questions.length;
    const passPercent = 50; // Passing criteria (50%)
    const userPercent = (score / total) * 100;

    if (userPercent >= passPercent) {
        // ===== Passed: Show Congratulations Card =====
        const congratsHTML = `
            <div class="congrats-card">
                <h1>🎉 Congratulations! 🎉</h1>
                <p>You scored <strong>${score}/${total}</strong> (${Math.round(userPercent)}%)</p>
                <p>You have passed the test!</p>
                <button class="btn btn-success mt-3" onclick="location.reload()">Retake Quiz</button>
            </div>
        `;
        $("#quizQuestion").html(congratsHTML);
    } else {
        // ===== Failed: Show Better Luck Message =====
        const failHTML = `
            <div class="congrats-card" style="background: linear-gradient(135deg, #f5c6cb, #f1b0b7);">
                <h1>😔 Better Luck Next Time!</h1>
                <p>You scored <strong>${score}/${total}</strong> (${Math.round(userPercent)}%)</p>
                <p>Don't worry, try again to improve your score.</p>
                <button class="btn btn-warning mt-3" onclick="location.reload()">Retry Quiz</button>
            </div>
        `;
        $("#quizQuestion").html(failHTML);
    }

    // Hide buttons & sidebar
    $("#prevBtn,#nextBtn,#submitBtn,#questionSidebar,#meritBar,#meritText,#timer").hide();

    // Optional: Confetti only if passed
    if (userPercent >= passPercent) startConfetti();
});


// ===== Initial Render =====
renderQuestion();
