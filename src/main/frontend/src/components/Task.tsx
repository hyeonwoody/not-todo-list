
import {Draggable} from 'react-beautiful-dnd';
import { Todo, todoProvider } from "../TodoProvider.tsx";
import { useSetRecoilState } from "recoil";
import styled from "styled-components";

interface TaskProps {
    index: number;
    category: string;
    task: Todo;
}

const Container = styled.div`
    width: 322px;
    height: 40px;
  border-radius: 10px;
  padding: 8px;
  color: #FFF;
  margin-bottom: 8px;

  background-color: ${(props) => bgcolorChange(props)};
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
`;

const TextContent = styled.div``;

function bgcolorChange(props) {
    return props.isDragging
        ? "#C1CFE5" : props.isDraggable
            ? props.isBacklog
                ? "#5B7096"
                : "#DCDCDC"
            : "#5B7096";
}

export default function Task({ index, task, category }: TaskProps) {
    const setTodos = useSetRecoilState(todoProvider);

    return (
        <Draggable draggableId={`${task.id}`} key={task.id} index={index}>
            {(provided, snapshot) => (
                <Container
                    {...provided.draggableProps}
                    {...provided.dragHandleProps}
                    ref={provided.innerRef}
                    isDragging={snapshot.isDragging}
                >
                        <TextContent>{task.text}</TextContent>
                    {provided.placeholder}
                </Container>
            )}
        </Draggable>
    );
}