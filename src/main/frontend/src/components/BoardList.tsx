import { Droppable } from "react-beautiful-dnd";
import styled from "styled-components";
import { ITodoState } from "../TodoProvider";
import Board from "./Board.tsx";

interface BoardListProps {
    todos: ITodoState;
}

const BoardListContainer = styled.section`
  margin: 2rem 0;
  width: 90%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(20rem, auto));
  grid-gap: 0.5rem;
`;

function BoardList({ todos }: BoardListProps) {
    const categories = Object.keys(todos);

    return (
        <Droppable
            type="categories"
            key="categories"
            droppableId="categories"
            direction="horizontal"
        >
            {(provided, snapshot) => (
                <BoardListContainer
                    ref={provided.innerRef}
                    {...provided.droppableProps}
                >
                    {categories.map((board, index) => (
                        <Board key={board} index={index} board={board} />
                    ))}
                    {provided.placeholder}
                    <Board key={2} index={2} board={2} />
                </BoardListContainer>
            )}
        </Droppable>
    );
}

export default BoardList;