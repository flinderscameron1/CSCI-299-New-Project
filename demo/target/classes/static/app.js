const startButton = document.getElementById("start");
const hitButton = document.getElementById("hit");
const stayButton = document.getElementById("stay");
const dealerCards = document.getElementById("dealer-cards");
const playerCards = document.getElementById("player-cards");
const pointsDisplay = document.getElementById("current-points");


startButton.addEventListener("click", () => 
{
    const bet = parseInt(prompt("Enter your bet:"));

    if (isNaN(bet) || bet <= 0) 
    {
        alert("Please enter a valid bet amount.");
        return; // Prevent proceeding if the bet is invalid
    }

    fetch("http://localhost:8080/blackjack/bet", 
    {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ bet: bet })
    })
        .then(res => res.json())
        .then(betData => {
            if (betData.error) 
            {
                alert(betData.error);
            } 
            else 
            {
                pointsDisplay.textContent = `Points: ${betData.points}`;
                alert(`Bet placed: ${betData.bet}`);

                fetch("http://localhost:8080/blackjack/start", { method: "POST" })
                    .then(res => res.json())
                    .then(gameData => 
                    {
                        if (gameData.error) 
                        {                        
                            alert(gameData.error);
                        } 
                        else 
                        {
                            dealerCards.textContent = "Hidden, " + gameData.dealerHand.join(", ");
                            playerCards.textContent = gameData.playerHand.join(", ");
                            hitButton.disabled = false;
                            stayButton.disabled = false;
                            startButton.disabled = true;
                        }
                    });
            }
        });
});

hitButton.addEventListener("click", () => 
{
    fetch("http://localhost:8080/blackjack/hit", { method: "POST" })
        .then(res => res.json())
        .then(data => 
        {
            if (data.result) 
            {
                alert(data.result);
                pointsDisplay.textContent = `Points: ${data.points}`;
                hitButton.disabled = true;
                stayButton.disabled = true;
                startButton.disabled = false;
            } 
            else 
            {
                playerCards.textContent = data.playerHand.join(", ");
            }
        });
});

stayButton.addEventListener("click", () => 
{
    fetch("http://localhost:8080/blackjack/stay", { method: "POST" })
        .then(res => res.json())
        .then(data => 
        {
            dealerCards.textContent = data.dealerHand.join(", ");
            alert(data.result);
            pointsDisplay.textContent = `Points: ${data.points}`;
            hitButton.disabled = true;
            stayButton.disabled = true;
            startButton.disabled = false;
        });
});