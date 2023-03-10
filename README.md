<p align = "center">МИНИСТЕРСТВО НАУКИ И ВЫСШЕГО ОБРАЗОВАНИЯ
РОССИЙСКОЙ ФЕДЕРАЦИИ
ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ
ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ ВЫСШЕГО ОБРАЗОВАНИЯ
«САХАЛИНСКИЙ ГОСУДАРСТВЕННЫЙ УНИВЕРСИТЕТ»</p>
<br><br><br><br><br><br>
<p align = "center">Институт естественных наук и техносферной безопасности<br>Кафедра информатики<br>Хроменков Владимир Александрович</p>
<br><br><br>
<p align = "center">Лабораторная работа №3<br>«Жизненный цикл `activity`»<br>01.03.02 Прикладная математика и информатика</p>
<br><br><br><br><br><br><br><br><br><br><br><br>
<p align = "right">Научный руководитель<br>
Соболев Евгений Игоревич</p>
<br><br><br>
<p align = "center">г. Южно-Сахалинск<br>2023 г.</p>

***
# <p align = "center">Оглавление</p>
- [Цели и задачи](#цели-и-задачи)
- [Решение задач](#решение-задач)
    - [Запись ответов](#recording_a_response)
    - [Оценка ответов](#response_score)
- [Вывод](#вывод)

***

# <p align = "center">Цели и задачи</p>

1.  Предотвращение ввода нескольких ответов.
После того как пользователь введет ответ на вопрос, заблокируйте кнопки этого вопроса, чтобы предотвратить возможность ввода нескольких ответов. 
 
2.	Вывод оценки.пки возврата
После того как пользователь введет ответ на все вопросы, отобразите уведомление с процентом правильных ответов. 

***

# <p align = "center">Решение задач</p>

## Запись ответов

Первым делом я модернезировал класс `Question` для того, чтобы хранить ответ пользователя. По умолчанию ответ будет пустым:

```kotlin
    data class Question(@StringRes val textResId: Int, val answer : Boolean) {
+++     var isAnswered: Boolean? = null;
    }
```

Далее в `MainActivity` при ответе пользователся я сохраняю его ответ и вызываю метод для блокировки кнопок:

```kotlin
//Метод для обработки действий при нажатии на кнопку
private fun trueButton(){
    if(questionBank[currentindex].answer)
        showToast(R.string.currect_toast);
    else
        showToast(R.string.incurrect_toast);

    //Записать ответ пользователя
    writeAnswer(true);
    //Перейти к ледующему вопросу
    nextQuestion();
}

private fun nextQuestion(){
    currentindex++;
    if(currentindex == questionBank.size) currentindex = questionBank.size - 1;

    val questionTextResId = questionBank[currentindex].textResId;
    questionTextView.setText(questionTextResId);

    //Обновить состояние кнопок в зависимости, ответили ли мы на текущий вопрос или нет
    checkAndLockButtons();
}

private fun writeAnswer(answer : Boolean){
    //Записываем ответ
    questionBank[currentindex].isAnswered = answer;
}

private fun checkAndLockButtons(){
    //Проверяем ответили ли мы
    if (questionBank[currentindex].isAnswered != null){
        //Блокеируем кнопки
        true_button.isClickable = false;
        false_button.isClickable = false;

        true_button.isEnabled = false;
        false_button.isEnabled = false;
    }
    else{
        //Разблокируем кнопки
        true_button.isClickable = true;
        false_button.isClickable = true;

        true_button.isEnabled = true;
        false_button.isEnabled = true;
    }
}
```

## Оценка ответов

Модернезируем методы при нажатии кнопок и добавим проверку на то, что мы ответили на все вопросы. 
Затем мы выведем длинный Toast с дробью и процентами привильных ответов

```kotlin
//Проверка, что на все вопросы был дан ответ
private fun isAllAnswered() : Boolean {
    for(quest in questionBank){
        if(quest.isAnswered == null) return false;
    }
    return true;
}

//Подсчет количества верноотвеченных вопросов
private fun countTrueAnswered() : Int {
    var count : Int = 0;
    for(quest in questionBank){
        if(quest.isAnswered == quest.answer) count++;
    }
    return count;
}

//Комбиноирование провреки, подсчета и вывод результата
private fun checkAndShowAnswered(){
    if(isAllAnswered()) {
        var count : Int = countTrueAnswered();
        var max : Int = questionBank.size;
        var procent: Double = ((count.toDouble() / max) * 10000).roundToInt() / 100.0;
        
        showToast("Вы ответили верно: " + count + "/" + max + "  -  " + procent + "%")
    }
}
```

Для работы всей системы вызывается только метод `checkAndShowAnswered()`.
Вызывается он при смене ответе на вопрос, а каждый ответ вызывается в свою очередь метод `nextQuestion()`. Туда я и поместил вызов `checkAndShowAnswered()`.

```kotlin
private fun nextQuestion(){
    currentindex++;
    if(currentindex == questionBank.size) currentindex = questionBank.size - 1;

    val questionTextResId = questionBank[currentindex].textResId;
    questionTextView.setText(questionTextResId);

    checkAndLockButtons();
    checkAndShowAnswered();
}
```

Так же перегрузил метод для вывода `Toast`:

```kotlin
//Старый
private fun showToast(text: Int){
    toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
    toast.show();
}

//Добавленный
private fun showToast(text: String){
    toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.show();
}
```

***

# <p align = "center">Вывод</p>

Выполнив *лабораторную работу №3*, совершенствую навыки работы с языком `Kotlin` и проектированием MVC архетиктуры приложения. 
