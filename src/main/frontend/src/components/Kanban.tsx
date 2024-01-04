import {useState} from 'react';
import {DragDropContext} from 'react-beautiful-dnd'

import Board from "./Board.tsx";

export default function Kanban(){

    const [completed, setCompleted] = useState([]);
    const [incomplete, setIncomplete] = useState([]);



    return (
            <DragDropContext>


                <div
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        flexDirection: "row",
                    }}
                >
                    <Board title={"TO DO"} tasks={incomplete} id={"1"}/>
                    <Board title={"TO DaO"} tasks={completed} id={"2"}/>
                </div>
            </DragDropContext>
    );
}