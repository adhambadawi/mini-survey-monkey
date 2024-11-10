$(document).ready(function() {
    let questionIndex = 0;  // Track the index of each question

    // Add a new question when the "Add Question" button is clicked
    $('#add-question').click(function() {
        questionIndex++;

        const questionHtml = `
            <div class="question-set" data-question-index="${questionIndex}">
                <!-- Setup based on question type -->
                <label for="questionText">Question:</label>
                <input type="text" name="questions[${questionIndex}].text" required />
                <br>

                <label for="questionType">Question Type:</label>
                <select name="questions[${questionIndex}].type" class="question-type">
                    <option value="OPEN_ENDED">Open-ended</option>
                    <option value="NUMBER_RANGE">Number Range</option>
                    <option value="MULTIPLE_CHOICE">Multiple Choice</option>
                </select>
                <br>

                <div class="dynamic-inputs"></div>
                
                <button type="button" class="add-option" data-question-index="${questionIndex}" style="display:none;">Add Option</button>
                <br>
                <br>
                <br>
            </div>
        `;

        // Append the new question HTML to the question container
        $('#questions-container').append(questionHtml);

        // Dynamically add the question type specific fields
        const questionSet = $(`.question-set[data-question-index=${questionIndex}]`);
        const questionTypeSelect = questionSet.find('.question-type');
        const addOptionButton = questionSet.find('.add-option');  // Get the "Add Option" button for this question

        // Handle the change event for question type
        questionTypeSelect.change(function() {
            // Setup and clear any previously added inputs
            const selectedType = $(this).val();
            const dynamicInputs = questionSet.find('.dynamic-inputs');
            dynamicInputs.empty();

            // Hide add option button if question type is not MCQ
            if (selectedType === 'MULTIPLE_CHOICE') {
                addOptionButton.show();
            } else {
                addOptionButton.hide();
            }

            // Add fields based on the selected question type
            if (selectedType === 'NUMBER_RANGE') {
                //Use min and max value for range
                dynamicInputs.append(`
                    <label>Min Value:</label><input type="number" name="questions[${questionIndex}].minValue" /><br>
                    <label>Max Value:</label><input type="number" name="questions[${questionIndex}].maxValue" />
                `);
            } else if (selectedType === 'MULTIPLE_CHOICE') {
                //Start MCQ with 2 options
                dynamicInputs.append(`
                    <label>Option 1:</label><input type="text" class="option-input" name="questions[${questionIndex}].options[0]" /><br>
                    <label>Option 2:</label><input type="text" class="option-input" name="questions[${questionIndex}].options[1]" />
                `);
            }
        });

        // Trigger the change event immediately to load default inputs
        questionTypeSelect.trigger('change');
    });

    // Add options for a specific MCQ
    // Each MCQ will have its own add options button
    $(document).on('click', '.add-option', function() {
        // Get the current question index when clicking button
        const questionIndex = $(this).data('question-index');

        // Track # of options in the question set so you could number them correctly
        const questionSet = $(`.question-set[data-question-index="${questionIndex}"]`);
        let optionIndex = questionSet.find('.option-input').length;

        // Append a new option to the specific question
        const optionHtml = `
            <br><label>Option ${optionIndex + 1}: </label>
            <input type="text" class="option-input" name="questions[${questionIndex}].options[${optionIndex}]" />`;

        // add new option into dynamic input container
        questionSet.find('.dynamic-inputs').append(optionHtml);
    });
});
