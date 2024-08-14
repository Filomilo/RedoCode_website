INSERT INTO users (nick_name, user_type, password, email)
VALUES ('sunny', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'sunny@mail.com'),
       ('shadow', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'shadow@mail.com'),
       ('sparky', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'sparky@mail.com'),
       ('whisper', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'whisper@mail.com'),
       ('blaze', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'blaze@mail.com'),
       ('frosty', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'frosty@mail.com'),
       ('echo', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'echo@mail.com'),
       ('nova', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'nova@mail.com'),
       ('shimmer', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'shimmer@mail.com'),
       ('phoenix', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'phoenix@mail.com'),
       ('neptune', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'neptune@mail.com'),
       ('zenith', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'zenith@mail.com'),
       ('avalanche', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'avalanche@mail.com'),
       ('sapphire', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'sapphire@mail.com'),
       ('mystic', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'mystic@mail.com'),
       ('raptor', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'raptor@mail.com'),
       ('thunder', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'thunder@mail.com'),
       ('enigma', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'enigma@mail.com'),
       ('infinity', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'infinity@mail.com'),
       ('quasar', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'quasar@mail.com'),
       ('laptop', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'laptop@mail.com');



INSERT INTO public.programming_languages (name)
VALUES ('java'),
       ('cpp'),
       ('js');
--
INSERT INTO public.excersizes (author_id,
                               excersize_name,
                               description,
                               input_type,
                               output_type,
                               ram_Mb,
                               time_for_task_min,
                               amount_of_auto_tests,
                               array_x_length_range_min,
                               array_x_length_range_max,
                               array_y_length_range_min,
                               array_y_length_range_max,
                               value_range_min,
                               value_range_max,
                               max_execution_time_ms)
VALUES (1, --author_id
        'fibonachi sequance', --excersize_name
        'Create funciton that returns number at point of fibocnahi squnace so 1->0 2->1 3->1 4->2 5->3 and do on', --description
        0, --input_type
        0, -- output_type
        512, --ram_Mb
        30, --time_for_task
        10, --amount_of_auto_tests
        0, --array_x_length_range_min
        0, --array_x_length_range_max
        0, --array_y_length_range_min
        0, --array_y_length_range_max
        10, --value_range_min
        40, --value_range_max,
        1000 -- max executin time ms
       );

INSERT INTO public.exercise_tests (excersize, id, expected_output, input)
VALUES (1, DEFAULT, '0', '1'),
       (1, DEFAULT, '1', '2'),
       (1, DEFAULT, '1', '3'),
       (1, DEFAULT, '2', '4'),
       (1, DEFAULT, '3', '5'),
       (1, DEFAULT, '5', '6'),
       (1, DEFAULT, '8', '7');
INSERT INTO public.solution_programs (id, excersize, language_id, code)
VALUES (DEFAULT,
        1,
        2,
        e 'int solution(int val)
{
    int* arr=new int[val];

if(val>=0)
  arr[0]=0;
if (val>=2)
   arr[1]=1;
for(int i=2;i<val;i++)
    {
        arr[i]=arr[i-1]+arr[i-2];
}
    return arr[val-1];
}'),
       (DEFAULT,
        1,
        3,
        e 'function solution(val) {
    let arr = new Array(val);

    if (val >= 0)
        arr[0] = 0;
    if (val >= 2)
        arr[1] = 1;
for (let i = 2; i < val; i++) {
        arr[i] = arr[i - 1] + arr[i - 2];
}
    return arr[val - 1];
}')
;


