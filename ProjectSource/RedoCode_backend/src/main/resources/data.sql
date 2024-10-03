INSERT INTO public.programming_languages (name)
VALUES ( 'java'),('cpp'),('js');

INSERT INTO users (nick_name, user_type, password, email, profile_pic) VALUES
                                                              ('sunny', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'sunny@mail.com',null),
                                                              ('shadow', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'shadow@mail.com',null),
                                                              ('sparky', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'sparky@mail.com',null),
                                                              ('whisper', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'whisper@mail.com',null),
                                                              ('blaze', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'blaze@mail.com',null),
                                                              ('frosty', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'frosty@mail.com',null),
                                                              ('echo', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'echo@mail.com',null),
                                                              ('nova', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'nova@mail.com',null),
                                                              ('shimmer', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'shimmer@mail.com',null),
                                                              ('phoenix', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'phoenix@mail.com',null),
                                                              ('neptune', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'neptune@mail.com',null),
                                                              ('zenith', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'zenith@mail.com',null),
                                                              ('avalanche', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'avalanche@mail.com',null),
                                                              ('sapphire', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'sapphire@mail.com',null),
                                                              ('mystic', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'mystic@mail.com',null),
                                                              ('raptor', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'raptor@mail.com',null),
                                                              ('thunder', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'thunder@mail.com',null),
                                                              ('enigma', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'enigma@mail.com',null),
                                                              ('infinity', 0, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'infinity@mail.com',null),
                                                              ('quasar', 1, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'quasar@mail.com',null),
                                                              ('laptop', 2, '$2a$10$Y83STWCLUoIgeGDitOGMS.wuei4hViRP0YVCLYstf2vdpPRpjRaYi', 'laptop@mail.com',null);




--
INSERT INTO public.excersizes (
    author_id,
    excersize_name,
    description,
    input_type,
    output_type,
    ram_Mb,
    amount_of_auto_tests,
    array_x_length_range_min,
    array_x_length_range_max,
    array_y_length_range_min,
    array_y_length_range_max,
    value_range_min,
    value_range_max,
    max_execution_time_ms
)
VALUES (1, --author_id
        'fibonachi sequance', --excersize_name
        'Create funciton that returns number at point of fibocnahi squnace so 1->0 2->1 3->1 4->2 5->3 and do on', --description
        0, --input_type
        0, -- output_type
        512, --ram_Mb
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
INSERT INTO public.solution_programs (id, excersize, language_id, code,Avg_execution_time,solution_author,date)
VALUES
(
           DEFAULT,
           1,
           2,
           e'int solution(int val)
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
}',
 300,
 1,
 CURRENT_DATE
),
(
    DEFAULT,
    1,
    3,
    e'function solution(val) {
    let arr = new Array(val);

    if (val >= 0)
        arr[0] = 0;
    if (val >= 2)
        arr[1] = 1;
for (let i = 2; i < val; i++) {
        arr[i] = arr[i - 1] + arr[i - 2];
}
    return arr[val - 1];
}',
 300
    ,1,
 CURRENT_DATE
),(
    DEFAULT,
    1,
    3,
    e'function solution(val) {
    let arr = new Array(val);

    if (val >= 0)
        arr[0] = 0;
    if (val >= 2)
        arr[1] = 1;
for (let i = 2; i < val; i++) {
        arr[i] = arr[i - 1] + arr[i - 2];
}
    return arr[val - 1];
}',
    300
    ,4,
    CURRENT_DATE
)
    ;

-- INSERT INTO Media (data)
-- VALUES (LOAD_FILE('/DefaultMedia/sunny.png'));

SHOW data_directory;

INSERT INTO Media (uuid, data, extension)
VALUES (
           gen_random_uuid(),
           decode('/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAcFBQYFBAcGBQYIBwcIChELCgkJChUPEAwRGBUaGRgVGBcbHichGx0lHRcYIi4iJSgpKywrGiAvMy8qMicqKyr/2wBDAQcICAoJChQLCxQqHBgcKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKir/wAARCAAgACADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD6C1LU1sQscQWS5kBKIxwAB1Zj2A/XoKxpboz5+03dxIT2ikMCr9AhDfmxrn9W1tpPEF6C/HmGIZ/hEZK4+mQx/wCBVX/tI/3q+DzTNMTOtKnSfLFaep7FLCcsU3udRFdNBj7Nd3ERHaWRp1b6hyW/JhWzpmprfBopQsdzGAXRWyCD0ZT3U4P0xg159/aR/vVZ0jW2j8QWahuPMER56hyFx+e0/hTyvNMRCtGnVfNF6egVcJeLa3MjxbZvpviS63KQkkhmU+ocls/mWH4VmrKxHDV6h4u0i31PTC7xO1xED5bxpuPupHcGvKzpl4rlY7e9hwekls7D8CB/Stsxy6rTqynCN4vXTU68JiqdSmoydmiRpWAyWxWn4Rs31HxJa7QSkcglY+gQhs/ntH41lx6RezTKjwXcuTjCWzqv4sR/SvVfC+hW+jacCik3EoHmSMuD7KB2Aoy7LqtSrGc42itddAxeKp06bjF3bP/Z', 'base64'),
           'jpg'
       );

UPDATE USERS SET
    profile_pic= (SELECT uuid FROM Media  LIMIT 1)
WHERE  users.nick_name='sunny';