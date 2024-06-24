INSERT INTO users (user_name,user_type) VALUES
                                  ('sunny',0),
                                  ('shadow',1),
                                  ('sparky',2),
                                  ('whisper',0),
                                  ('blaze',1),
                                  ('frosty',2),
                                  ('echo',0),
                                  ('nova',1),
                                  ('shimmer',2),
                                  ('phoenix',0),
                                  ('neptune',1),
                                  ('zenith',2),
                                  ('avalanche',0),
                                  ('sapphire',1),
                                  ('mystic',2),
                                  ('raptor',0),
                                  ('thunder',1),
                                  ('enigma',2),
                                  ('infinity',0),
                                  ('quasar',1),
                                  ('laptop',2);


INSERT INTO public.programming_languages (name)
VALUES ( 'java'),('cpp'),('js');
--
INSERT INTO public.excersizes (
    author_id,
    excersize_name,
    description,
    input_type,
    output_type,
    ram_Mb,
    time_for_task,
    amount_of_auto_tests,
    array_x_length_range_min,
    array_x_length_range_max,
    array_y_length_range_min,
    array_y_length_range_max,
    value_range_min,
    value_range_max
)
VALUES (1, --author_id
        'fibonachi sequance', --excersize_name
        'Create funciton that returns number at point of fibocnahi squnace so 1->0 2->1 3->1 4->2 5->3 and do on', --description
        0, --input_type
        0, -- output_type
        512, --ram_Mb
        '01:30:00', --time_for_task
        10, --amount_of_auto_tests
        0, --array_x_length_range_min
        0, --array_x_length_range_max
        0, --array_y_length_range_min
        0, --array_y_length_range_max
        10, --value_range_min
        40 --value_range_max
       );

INSERT INTO public.exercise_tests (excersize, id, expected_output, input)
VALUES (1, DEFAULT, '{"value":0}', '{"value":1}'),
       (1, DEFAULT, '{"value":1}', '{"value":2}'),
       (1, DEFAULT, '{"value":1}', '{"value":3}'),
       (1, DEFAULT, '{"value":2}', '{"value":4}'),
       (1, DEFAULT, '{"value":3}', '{"value":5}'),
       (1, DEFAULT, '{"value":5}', '{"value":6}'),
       (1, DEFAULT, '{"value":8}', '{"value":7}');
INSERT INTO public.solution_programs (id, excersize, language_id, code)
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
}'
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
}'
)

;


