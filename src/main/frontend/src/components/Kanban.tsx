import {useEffect, useState} from 'react';
import {todoProvider} from "../TodoProvider.tsx";
import {DragDropContext, DropResult} from 'react-beautiful-dnd'
import styled from "styled-components";
import BoardList from "./BoardList.tsx";

const Wrapper = styled.main`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export default function Kanban(){

    const [todos, setTodos] = useState(todoProvider);

    const [completed, setCompleted] = useState([]);
    const [incomplete, setIncomplete] = useState([]);

    useEffect(() => {
        fetch("https://jsonplaceholder.typicode.com/todos")
            .then((response) => response.json())
            .then((json) => {
                setCompleted(json.filter((task) => task.completed));
                setIncomplete(json.filter((task) => !task.completed));
            });
    }, []);

    function findItemById(id, array) {
        return array.find((item) => item.id == id);
    }


    function removeItemById(id, array) {
        return array.filter((item) => item.id != id);
    }

    const handleDragEnd = (info: DropResult) => {
        const { type, source, destination } = info;

        if (!destination) return;

        if (type === "categories") {
            setTodos((allBoards) => {
                const categoryEntries = Object.entries(allBoards);
                const [excludedCategory] = categoryEntries.splice(source.index, 1);
                categoryEntries.splice(destination.index, 0, excludedCategory);

                const convertCategoryEntriesToObject = categoryEntries.reduce(
                    (obj, [key, value]) => ({ ...obj, [key]: value }),
                    {}
                );

                //saveLocalStorage(convertCategoryEntriesToObject);

                return convertCategoryEntriesToObject;
            });
        } else {
            setTodos((allTodos) => {
                if (source.droppableId === destination.droppableId) {
                    const [categoryName] = source.droppableId.split("-");
                    const duplicatedCategory = [...allTodos[categoryName]];
                    const [excludedTask] = duplicatedCategory.splice(source.index, 1);
                    duplicatedCategory.splice(destination.index, 0, excludedTask);

                    const newTodos = {
                        ...allTodos,
                        [categoryName]: duplicatedCategory,
                    };

                    //saveLocalStorage(newTodos);

                    return newTodos;
                } else {
                    const [sourceCategoryName] = source.droppableId.split("-");
                    const [destinationCategoryName] = destination.droppableId.split("-");
                    const sourceCategory = [...allTodos[sourceCategoryName]];
                    const destinationCategory = [...allTodos[destinationCategoryName]];
                    const [excludedTask] = sourceCategory.splice(source.index, 1);
                    destinationCategory.splice(destination.index, 0, excludedTask);

                    const newTodos = {
                        ...allTodos,
                        [sourceCategoryName]: sourceCategory,
                        [destinationCategoryName]: destinationCategory,
                    };

                    //saveLocalStorage(newTodos);

                    return newTodos;
                }
            });
        }
    };
    return (
        <DragDropContext onDragEnd={handleDragEnd}>
            <Wrapper>
                <h2 style={{ textAlign: "center" }}>PROGRESS BOARD</h2>

                <BoardList todos={todos}>

                </BoardList>
            </Wrapper>
        </DragDropContext>
    );
}