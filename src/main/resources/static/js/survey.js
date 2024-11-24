document.addEventListener('DOMContentLoaded', function() {
    let questionIndex = 0;  // Track the index of each question

    // Add a new question when the "Add Question" button is clicked
    const addQuestionButton = document.getElementById('add-question');
    if (addQuestionButton) {
        addQuestionButton.addEventListener('click', function() {
            questionIndex++;

            const questionsContainer = document.getElementById('questions-container');

            const questionSet = document.createElement('div');
            questionSet.classList.add('question-set', 'mb-4');
            questionSet.dataset.questionIndex = questionIndex;

            questionSet.innerHTML = `
                <div class="mb-3">
                    <label for="questionText${questionIndex}" class="form-label">Question:</label>
                    <input type="text" name="questions[${questionIndex}].text" class="form-control" id="questionText${questionIndex}" required />
                </div>

                <div class="mb-3">
                    <label for="questionType${questionIndex}" class="form-label">Question Type:</label>
                    <select name="questions[${questionIndex}].type" class="form-select question-type" id="questionType${questionIndex}">
                        <option value="OPEN_ENDED">Open-ended</option>
                        <option value="NUMBER_RANGE">Number Range</option>
                        <option value="MULTIPLE_CHOICE">Multiple Choice</option>
                    </select>
                </div>

                <div class="dynamic-inputs"></div>

                <button type="button" class="btn btn-secondary add-option mt-2" data-question-index="${questionIndex}" style="display:none;">Add Option</button>
                <hr>
            `;

            // Append the new question to the container
            questionsContainer.appendChild(questionSet);

            // Get references to elements within the question set
            const questionTypeSelect = questionSet.querySelector('.question-type');
            const dynamicInputs = questionSet.querySelector('.dynamic-inputs');
            const addOptionButton = questionSet.querySelector('.add-option');

            // Handle the change event for question type
            questionTypeSelect.addEventListener('change', function() {
                const selectedType = this.value;
                dynamicInputs.innerHTML = '';

                // Hide or show the "Add Option" button based on question type
                if (selectedType === 'MULTIPLE_CHOICE') {
                    addOptionButton.style.display = 'inline-block';
                } else {
                    addOptionButton.style.display = 'none';
                }

                if (selectedType === 'NUMBER_RANGE') {
                    // Add min and max value inputs for number range questions
                    dynamicInputs.innerHTML = `
                        <div class="mb-3">
                            <label for="minValue${questionIndex}" class="form-label">Min Value:</label>
                            <input type="number" name="questions[${questionIndex}].minValue" class="form-control" id="minValue${questionIndex}" />
                        </div>
                        <div class="mb-3">
                            <label for="maxValue${questionIndex}" class="form-label">Max Value:</label>
                            <input type="number" name="questions[${questionIndex}].maxValue" class="form-control" id="maxValue${questionIndex}" />
                        </div>
                    `;
                } else if (selectedType === 'MULTIPLE_CHOICE') {
                    // Start with two options for multiple-choice questions
                    dynamicInputs.innerHTML = `
                        <div class="mb-3">
                            <label for="option${questionIndex}_0" class="form-label">Option 1:</label>
                            <input type="text" class="form-control option-input" name="questions[${questionIndex}].options[0]" id="option${questionIndex}_0" />
                        </div>
                        <div class="mb-3">
                            <label for="option${questionIndex}_1" class="form-label">Option 2:</label>
                            <input type="text" class="form-control option-input" name="questions[${questionIndex}].options[1]" id="option${questionIndex}_1" />
                        </div>
                    `;
                }
            });

            // Trigger the change event immediately to load default inputs
            questionTypeSelect.dispatchEvent(new Event('change'));

            // Add event listener to the "Add Option" button
            addOptionButton.addEventListener('click', function() {
                const questionIndex = this.dataset.questionIndex;
                const questionSet = document.querySelector(`.question-set[data-question-index="${questionIndex}"]`);
                const dynamicInputs = questionSet.querySelector('.dynamic-inputs');

                // Count existing options
                const optionInputs = dynamicInputs.querySelectorAll('.option-input');
                const optionIndex = optionInputs.length;

                // Create new option input
                const newOptionDiv = document.createElement('div');
                newOptionDiv.classList.add('mb-3');
                newOptionDiv.innerHTML = `
                    <label for="option${questionIndex}_${optionIndex}" class="form-label">Option ${optionIndex + 1}:</label>
                    <input type="text" class="form-control option-input" name="questions[${questionIndex}].options[${optionIndex}]" id="option${questionIndex}_${optionIndex}" />
                `;

                dynamicInputs.appendChild(newOptionDiv);
            });
        });
    } else {
        console.error('Add Question button not found. Please ensure it has the correct ID.');
    }
});
