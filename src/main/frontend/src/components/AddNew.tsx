import styled from "styled-components";
import { useForm } from "react-hook-form";

interface InputProps {
    onValid: (name: string) => void;
    name: string;
    placeholder: string;
}

interface Name {
    [name: string]: string;
}

export const Form = styled.form`
    width: 60%;
    max-width: 25rem;
    margin-top: 2rem;
    display: flex;
    justify-content: center;
    position: relative;

    & > input {
        all: unset;
        width: 100%;
        height: 2rem;
        border: 1px solid #707c83;
        border-radius: 0.5rem;
        padding: 0.5rem 1rem;
        transition: border-color 0.2s ease-in-out;

        &:focus {
            border-color: #fff;
        }
    }

    & > button {
        all: unset;
        position: absolute;
        top: 1.1rem;
        right: 0.5rem;
    }

    @media (max-width: 768px) {
        width: 80%;
    }
`;

function Input({ onValid, name, placeholder }: InputProps) {
    const { register, handleSubmit, setValue } = useForm<Name>();
    const onSubmit = (obj: Name) => {
        onValid(obj[name]);
        setValue(name, "");
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)}>
            <input
                {...register(name, { required: true })}
                type="text"
                placeholder={placeholder}
            />
            <button>
            </button>
        </Form>
    );
}

export default Input;