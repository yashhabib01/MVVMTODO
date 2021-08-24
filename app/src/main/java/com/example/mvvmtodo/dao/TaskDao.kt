package com.example.mvvmtodo.dao

import androidx.room.*
import com.example.mvvmtodo.data.SortOrder
import com.example.mvvmtodo.data.Task
import com.example.mvvmtodo.ui.task.TaskViewModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun  getTask(query:String, sortOrder: SortOrder, hideCompleted: Boolean) : Flow<List<Task>> =
        when(sortOrder){
            SortOrder.BY_DATE -> getTaskSortedByDateCreated(query,hideCompleted)
            SortOrder.BY_NAME -> getTaskSortedByName(query,hideCompleted)
        }

    @Query("SELECT * FROM task_table  WHERE (completed != :hideCompleted OR completed == 0) AND  name LIKE '%' || :searchQuery || '%' ORDER BY important DESC,name")
    fun getTaskSortedByName(searchQuery:String,hideCompleted :Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table  WHERE (completed != :hideCompleted OR completed == 0) AND  name LIKE '%' || :searchQuery || '%' ORDER BY important DESC,`create`")
    fun getTaskSortedByDateCreated(searchQuery:String,hideCompleted :Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table WHERE completed = 1")
    suspend fun deleteCompletedTasks()
}