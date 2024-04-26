INSERT INTO users (user_name) VALUES
                                  ('sunny'),
                                  ('shadow'),
                                  ('sparky'),
                                  ('whisper'),
                                  ('blaze'),
                                  ('frosty'),
                                  ('echo'),
                                  ('nova'),
                                  ('shimmer'),
                                  ('phoenix'),
                                  ('neptune'),
                                  ('zenith'),
                                  ('avalanche'),
                                  ('sapphire'),
                                  ('mystic'),
                                  ('raptor'),
                                  ('thunder'),
                                  ('enigma'),
                                  ('infinity'),
                                  ('quasar');


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
    value_range_max,
    string_format_mask
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
        40, --value_range_max
        0 --string_format_mask
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
--     (
--         DEFAULT,
--         1,
--         3,
--         e'function solution(val){
-- var fib = [0, 1];
-- for (var i = 2; i <= val; i++) {
--   fib[i] = fib[i - 2] + fib[i - 1];
-- }
-- return fib.slice(0,val)
-- }'),
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
)

;







-- INSERT INTO public.excersizes (author_id, excersize_name,description,input_type,output_type)
-- VALUES (1,  'Hello world','return as and output a prase "Hello world!" ',0,1);
-- ,
--        (2, 'Fibonacci'),
--        (3,'Sorting'),
--        (6,'linked list implementation'),
--        (2,'prime number generator'),
--        (4, 'Palindrome Checker'),
--        (5, 'Binary Search Tree Implementation'),
--        (6, 'Matrix Multiplication'),
--        (7, 'Stack Implementation'),
--        (8, 'Queue Implementation'),
--        (1, 'Graph Traversal (BFS/DFS)'),
--        (1, 'String Reversal'),
--        (1, 'Merge Sort'),
--        (2, 'Tree Traversal'),
--        (4, 'Hash Map Implementation'),
--        (5, 'Binary Tree Height Calculation'),
--        (6, 'Depth-First Search (DFS)'),
--        (7, 'Breadth-First Search (BFS)'),
--        (8, 'Binary Search Algorithm'),
--        (9, 'Recursion Exercises'),
--        (5, 'Linked List Reversal'),
--        (1, 'Trie Data Structure Implementation'),
--        (2, 'Dijkstra Algorithm Implementation'),
--        (3, 'Topological Sorting Algorithm Implementation'),
--        (4, 'Heap Implementation'),
--        (5, 'Binary Tree Diameter Calculation');
--
--
--
-- INSERT INTO public.excersize_languages (excersize_id, language_id)
-- VALUES (1, 2),
--        (1,1),
--        (3,1),
--        (2,1);
--
-- INSERT INTO public.exercise_attempts (succes, excersize_id, finished_at, started_at, user_id)
-- VALUES (false, 1, '2024-01-24 21:48:23.000000', '2024-03-24 21:46:31.000000', 1),
--        (false, 1, '2024-01-25 21:48:23.000000', '2024-03-25 21:46:31.000000', 1),
--        (true, 2, '2024-01-25 10:15:45.000000', '2024-03-25 10:10:22.000000', 2),
--        (false, 3, '2024-01-26 15:30:10.000000', '2024-03-26 15:25:55.000000', 3),
--        (true, 4, '2024-01-27 08:12:36.000000', '2024-03-27 08:10:19.000000', 4),
--        (false, 5, '2024-01-28 14:55:40.000000', '2024-03-28 14:50:18.000000', 5),
--        (true, 1, '2024-01-29 19:20:15.000000', '2024-03-29 19:15:42.000000', 1),
--        (false, 2, '2024-01-30 12:03:28.000000', '2024-03-30 12:00:10.000000', 2),
--        (true, 3, '2024-01-31 23:45:50.000000', '2024-03-31 23:40:29.000000', 3),
--        (false, 4, '2024-02-01 07:28:14.000000', '2024-04-01 07:25:01.000000', 4),
--        (true, 5, '2024-02-02 17:10:22.000000', '2024-04-02 17:05:15.000000', 5),
--        (false, 1, '2024-02-03 20:40:38.000000', '2024-04-03 20:35:19.000000', 1),
--        (true, 2, '2024-02-04 09:14:55.000000', '2024-04-04 09:10:42.000000', 2),
--        (false, 3, '2024-02-05 14:22:10.000000', '2024-04-05 14:18:55.000000', 3),
--        (true, 4, '2024-02-06 11:08:36.000000', '2024-04-06 11:05:19.000000', 4),
--        (false, 5, '2024-02-07 16:45:40.000000', '2024-04-07 16:40:18.000000', 5),
--        (true, 1, '2024-02-08 22:10:15.000000', '2024-04-08 22:05:42.000000', 1),
--        (false, 2, '2024-02-09 13:43:28.000000', '2024-04-09 13:40:10.000000', 2),
--        (true, 3, '2024-02-10 04:15:50.000000', '2024-04-10 04:10:29.000000', 3),
--        (false, 4, '2024-02-11 18:28:14.000000', '2024-04-11 18:25:01.000000', 4),
--        (true, 5, '2024-02-12 09:10:22.000000', '2024-04-12 09:05:15.000000', 5);
--
--
-- INSERT INTO public.excersize_diffuculty_ratings (rating, excersize_id, user_id)
-- VALUES (1, 1, 5),
--        (2, 2, 1),
--        (5, 3, 2),
--        (4, 4, 3),
--        (1, 5, 4),
--        (2, 5, 5),
--        (5, 4, 6),
--        (4, 4, 9),
--        (3, 2, 8),
--        (2, 3, 9),
--        (1, 1, 7),
--        (4, 4, 7),
--        (5, 1, 8);

