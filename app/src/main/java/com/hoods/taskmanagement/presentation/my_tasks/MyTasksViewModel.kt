package com.hoods.taskmanagement.presentation.my_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoods.taskmanagement.data.local.models.Task
import com.hoods.taskmanagement.data.repository.TaskRepository
import com.hoods.taskmanagement.di.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MyTasksUiState(
    val tasksForSelectedTag: List<Task> = emptyList(),
    val selectedTag: TaskTag = TaskTag.WORK,
)

class MyTasksViewModel(
    private val taskRepository: TaskRepository = Graph.repository
) : ViewModel() {
    private val _selectedTag = MutableStateFlow(TaskTag.WORK)
    private val _allTasks = taskRepository.getAllTasks()
    val uiState: StateFlow<MyTasksUiState> = combine(
        _allTasks,
        _selectedTag
    ) { allTasks, selectedTag ->
        val filteredTasks = allTasks.filter {
            it.tags.equals(selectedTag.name, ignoreCase = true)
        }.sortedBy { it.isCompleted }
        MyTasksUiState(
            selectedTag = selectedTag,
            tasksForSelectedTag = filteredTasks
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyTasksUiState()
    )

    fun onTagChange(tag: TaskTag) {
        _selectedTag.value = tag
    }

    fun onTaskCheckedChange(task: Task, checked: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isCompleted = checked))
        }
    }
}




