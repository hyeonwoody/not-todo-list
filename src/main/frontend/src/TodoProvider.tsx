import { atom } from "recoil";

export interface Todo {
    id: number;
    text: string;
}

export interface ITodoState {
    [category: string]: Todo[];
}

export const todoProvider = atom<ITodoState>({
    key: "todoState",
    default: JSON.parse(localStorage.getItem("memo") as string) || {
        Todo: [],
        Doing: [],
        Done: [],
    },
});