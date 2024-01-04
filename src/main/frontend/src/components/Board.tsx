
import {Droppable} from "react-beautiful-dnd";
import styled from "styled-components";
import Task from "./Task.tsx"
// @ts-ignore
import Plus from "../assets/ph_plus-thin.svg";


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

const AddNew = styled.div`
    display: flex;
    align-items: center;
    height: 24px;
    width: 280px;
    color: white;

    & .rectangle {
        display: flex;  // Make it a flex container
        border: 1px solid;
        border-color: #ffffff;
        border-radius: 8px;
        height: 24px;
        width: 280px;
        justify-content: center;  // Center the content horizontally
        align-items: center;  // Center the content vertically
    }
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

export default function Board ({title, tasks, id}){
    return (<Container>
        <Title
            style={{
                position: "stick",
        }}
        >
            {title}
        </Title>
        {/*<AddNew>*/}
        {/*    <Rectangle>*/}
        {/*        <p>Add new Prohitbition</p>*/}
        {/*        <img src={Plus} alt="Plus Icon" style={{marginLeft: 'auto'}}/>*/}
        {/*    </Rectangle>*/}
        {/*</AddNew>*/}
        <Droppable droppableId={id}>

        {(provided, snapshot) => {
                <TaskList
                    ref={provided.innerRef}
                    {...provided.droppableProps}
                    isDraggingOver={snapshot.isDraggingOver}
                >
                    {tasks}
                    <Task task={{ id: 123, title:"Make Progress"}}
                          index={1}></Task>
                    {provided.placeholder}
                </TaskList>
            }}
        </Droppable>
    </Container>);
}