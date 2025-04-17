package eu.tonkov.quizappcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tonkov.quizappcompose.ui.theme.QuizAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppComposeTheme {
                QuizApp()
            }
        }

    }
}

fun generateQuestions(): List<Question> {
    return listOf(
        Question(
            "What is the capital of France?",
            listOf("Paris", "London", "Berlin", "Madrid"),
            0
        ),
        Question("What is the capital of Bulgaria?",
            listOf("Sofia", "Plovdiv", "Varna", "Burgas"),
            0
        ),
        Question("What is the capital of Germany?",
            listOf("Munich", "Berlin", "Frankfurt", "Hamburg"),
            1),
        Question("What is the capital of Italy?",
            listOf("Rome", "Milan", "Naples", "Turin"),
            0),
        Question("What is the capital of Spain?",
            listOf("Madrid", "Barcelona", "Valencia", "Seville"),
            0),
        Question("What is the capital of Portugal?",
            listOf("Lisbon", "Porto", "Braga", "Coimbra"),
            0),
        Question("What is the capital of Greece?",
            listOf("Athens", "Thessaloniki", "Patra", "Ioannina"),
            0),
        Question("What is the capital of Switzerland?",
            listOf("Bern", "Zurich", "Geneva", "Basel"),
            0),
        Question("What is the capital of Poland?",
            listOf("Warsaw", "Krakow", "Lodz", "Wroclaw"),
            0),
        Question("What is the capital of Hungary?",
            listOf("Budapest", "Debrecen", "Szeged", "Miskolc"),
            0)
    )
}

@Composable
fun QuestionCard(question: Question, selectedOption: Int, onOptionSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = question.question,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
                )


            question.options.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                        .selectable(selected = index == selectedOption, onClick = {onOptionSelected(index)}),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = index == selectedOption, onClick = null)
                    Text(modifier = Modifier.padding(start = 8.dp), text = option)
                }
            }
        }
    }
}

const val OPTION_NOT_SELECTED = -1

@Preview(showSystemUi = true)
@Composable
fun QuizApp(){
    val questions = generateQuestions()
    val context = LocalContext.current

    val selectedOptions = rememberSaveable(
        saver = listSaver(save = { it.toList() },
            restore = {it.toMutableStateList()})
    ) {
        mutableStateListOf<Int>().apply {
            for (i in questions.indices) {
                add(OPTION_NOT_SELECTED)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 40.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Text(text = "Quiz App", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
        itemsIndexed(questions) { index, question ->
           QuestionCard(question, selectedOptions[index], onOptionSelected = {selectedOptions[index] = it})
        }
        item{
            Button(modifier = Modifier.padding(top = 20.dp),
                onClick = {
                    var score = 0
                    questions.forEachIndexed { index, question ->
                        if (question.correctAnswerIndex == selectedOptions[index]){
                            score++
                        }
                    }
                    Toast.makeText(context, "Your score is $score out of ${questions.size}", Toast.LENGTH_LONG).show()
                }) {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
@Preview
fun QuestionCardPreview() {
    val question = Question(
        "What is the capital of France?",
        listOf("Paris", "London", "Berlin", "Madrid"),
        0)
    Column {
        Spacer(modifier = Modifier.padding(60.dp))
        QuestionCard(question = question, selectedOption = 3, onOptionSelected = {})
    }

}

