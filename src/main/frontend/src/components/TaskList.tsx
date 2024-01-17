import { Droppable } from "react-beautiful-dnd";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { todoProvider } from "../TodoProvider.tsx";
import Task from "./Task.tsx";

const TasksContainer = styled.ul`
  width: 100%;
  height: calc(100% - 3rem);
  padding: 1rem;
`;

function TaskList({ board }: { board: string }) {
    const todos = useRecoilValue(todoProvider);
    const tasks = todos[board];

    return (
        <Droppable key={`${board}-tasks`} droppableId={`${board}-tasks`}>
    {(provided, snapshot) => (
        <TasksContainer ref={provided.innerRef} {...provided.droppableProps}>
        {tasks.map((task, index) => (
            <Task index={index} key={task.id} task={task} category={board} />
        ))}
        {provided.placeholder}
        </TasksContainer>
    )}
    </Droppable>
);
}
export default TaskList;