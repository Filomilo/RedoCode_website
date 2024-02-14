import { createServer, Model } from "miragejs"
import type ExerciseType from "./types/ExerciseType";

export function makeServer({ environment = "development" } = {}) {

    const exerciseData:ExerciseType[] =[
        {
            name: "task1",
            language: ["c++"],
            difficulty: "hard",
            popularity: 222,
            id: 1,
        },
        {
            name: "task2",
            language: ["any"],
            difficulty: "hard",
            popularity: 222122,
            id: 2
        },
        {
            name: "task3",
            language: ["c++","java"],
            difficulty: "easy",
            popularity: 23,
            id: 3
        }
    ]
    

    const exerciseListHandler = (schema: any, request: any) => {
      return exerciseData;
  };
  
  const server = createServer({
    environment,


    routes() {
      // this.namespace = "http://localhost:9090"


      this.get("http://localhost:9090/exercises", exerciseListHandler);
    },
  })

  return server
}