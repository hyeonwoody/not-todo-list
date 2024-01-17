
import {Draggable} from "react-beautiful-dnd";
import {useSetRecoilState} from "recoil";
import styled from "styled-components";
import AddNew from "./AddNew.tsx";
import {todoProvider} from "../TodoProvider.tsx";


interface CategoryProps {
    board: string;
    index: number;
}



const Container = styled.div`
    background-color:#14181F;
    border-radius: 5.5px;
    width: 338px;
    height: 256px;
    overflow-y: scroll;
    -ms-overflow-style: none;
    scrollbar-width: none;
    
    display: flex;
    flex-direction: column;
    align-items: center;
    
    //Hide scroll bar
    -ms-overflow-style: none; /* for Internet Explorer, Edge */
    scrollbar-width: none; /* for Firefox */
    overflow-y: scroll;

    &::-webkit-scrollbar {
        display: none; /* for Chrome, Safari, and Opera */
    }
`;

const Title = styled.h3`
    padding: 8px;
    text-align: center;
    color: white;
    
    font-family: "Inter", Helvetica;
    font-size: 24px;
    font-weight: 400;
    left: 11px;
    letter-spacing: 0;
    line-height: normal;
    top: 5px;
`;

const Rectangle = styled.div`
    position: relative;
    display: flex;
    border: 1px solid;
    border-color: #ffffff;
    border-radius: 8px;
    height: 24px;
    width: 280px;
    justify-content: space-between;  // Center the content horizontally
    align-items: center;  // Center the content vertically
    padding: 0 8px;
    
`;

const TaskList = styled.div`
    padding: 3px;
    transition: background-color 0.2s ease;
    background-color: #5b7096;
    flex-grow: 1;
    min-height: 100px;
`;

export default function Board ({ board, index }: CategoryProps) {

    const setTodos = useSetRecoilState(todoProvider);

    const removeCategory = () => {
        setTodos((allCategories) => {
            const duplicatedCategories = { ...allCategories };
            delete duplicatedCategories[board];


            return duplicatedCategories;
        });
    };
    const handleValid = (text: string) => {
        text = text.trim();

        if (text === "") return;

        const newTask = {
            id: Date.now(),
            text,
        };

        setTodos((allBoards) => {
            const targetTasks = allBoards[board];
            const updateTargetTasks = [newTask, ...targetTasks];
            const newTodos = {
                ...allBoards,
                [board]: updateTargetTasks,
            };


            return newTodos;
        });
    };

        return (
            <Draggable draggableId={board} index={index} key={board}>
                {(provided, snapshot) => (
                    <Container
                        ref={provided.innerRef}
                        {...provided.draggableProps}
                        {...provided.dragHandleProps}
                        isDragging={snapshot.isDragging}
                    >
                        <Title>
                            <h3>{board}</h3>
                            <button onClick={removeCategory}>
                            </button>
                        </Title>
                        <AddNew
                            onValid={handleValid}
                            name={board}
                            placeholder={`Add task on ${board}`}
                        />
                        <TaskList board={board} />
                    </Container>
                )}
            </Draggable>
        );
}