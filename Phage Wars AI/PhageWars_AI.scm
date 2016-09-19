;;;; CSci 1901 Project - Fall 2013
;;;; PhageWars++ Player AI

;;;======================;;;
;;;  SUBMISSION DETAILS  ;;;
;;;======================;;;

;; List both partners' information below.
;; Leave the second list blank if you worked alone.
(define authors 
  '((
     "Xuanyu Zhang"   ;; Author 1 Name
     "zhan2223"   ;; Author 1 X500
     "XXXX"   ;; Author 1 ID
     "006"   ;; Author 1 Section
     )
    (
     "John Saja"   ;; Author 2 Name 
     "sajax003"   ;; Author 2 X500
     "XXXX"   ;; Author 2 ID
     "006"   ;; Author 2 Section
     )))


;;;====================;;;
;;;  Player-Procedure  ;;;
;;;====================;;;

(define player-procedure
  (let () 

    ;;===================;;
    ;; Helper Procedures ;;
    ;;===============================================================;;
    ;; Include procedures used by get-move that are not available in ;;
    ;;  the util.scm or help.scm file.                               ;;
    ;; Note: PhageWars++.scm loads util.scm and help.scm , so you do ;;
    ;;  not need to load it from this file.                          ;;
    ;; You also have access to the constants defined inside of       ;;
    ;;  PhageWars++.scm - Constants are wrapped in asterisks.        ;;
    ;;===============================================================;;

 
    ;; Returns a random-element from a list.
    (define (random-element lst)
      (list-ref lst (random (length lst))))
    
	;; Makes a random move
    (define (make-random-move player board)
      (let ((my-cells (get-cell-list player board)))
	(list (make-move (car (random-element my-cells)) 
	  (car (random-element board))))))

   ;; Gets the current count for any specified cell
    (define (get-current-count cell-number board)
      (inexact->exact (floor (list-ref (get-cell cell-number board) 4)))
      )

   ;; Gets the next turn count for any specified cell
    (define (next-turn-count cell-number board)
      (inexact->exact (floor (+ (list-ref (get-cell cell-number board) 4) (list-ref (get-cell cell-number board) 3))))
      )

   ;; Gets a list of ALL user cell values
    (define (get-all-user-cells board player)
        (define (helper my-cells)
            (if (null? my-cells)
               '()
                (if (>= (list-ref (car my-cells) 5) 0)
                    (cons (list-ref (car my-cells) 5) (helper (cdr my-cells)))
                          (helper (cdr my-cells)))))
                          (helper (get-cell-list player board)))

   ;; Gets list of opponent's cells
    (define (list-of-opponent-cells board player)
        (get-cell-list (other-player player) board))   

   ;; Gets the smallest opponent cell VALUE
     (define (smallest-opponent-cell-value board player)
         (define (helper list-of-opponent-cells)
            (if (null? list-of-opponent-cells)
                201
                (min (list-ref (car list-of-opponent-cells) 5)
                     (helper (cdr list-of-opponent-cells)))))
                     (helper (get-cell-list (other-player player) board)))

   ;; Gets the smallest user owned cell VALUE
     (define (smallest-user-cell-value board player)
         (define (helper my-cells)
            (if (null? my-cells)
                201
                (min (list-ref (car my-cells) 5)
                     (helper (cdr my-cells)))))
                     (helper (get-cell-list player board)))

   ;;Gets the largest opponent cell VALUE
      (define (largest-opponent-cell-value board player)
         (define (helper list-of-opponent-cells)
            (if (null? list-of-opponent-cells)
                0
                (max (list-ref (car list-of-opponent-cells) 5)
                     (helper (cdr list-of-opponent-cells)))))
                     (helper (get-cell-list (other-player player) board)))
  
   ;; Gets the largest user owned cell VALUE
    (define (largest-user-cell-value board player)
        (define (helper list-of-my-cells)
            (if (null? list-of-my-cells)
                0
                (max (list-ref (car list-of-my-cells) 5)
                     (helper (cdr list-of-my-cells)))))
                     (helper (get-cell-list player board)))

   ;; gets the smallest user owned cell NUMBER
    (define (smallest-user-cell board player)
        (define (helper my-cells)
            (if (= (list-ref (car my-cells) 5) (smallest-user-cell-value board player))
                (list-ref (car my-cells) 0)
                (helper (cdr my-cells))))
    (helper (get-cell-list player board)))

   ;; Gets the smallest opponent cell NUMBER
    (define (smallest-opponent-cell board player)
        (define (helper list-of-opponent-cells)
            (if (= (list-ref (car list-of-opponent-cells) 5) (smallest-opponent-cell-value board player))
                (list-ref (car list-of-opponent-cells) 0)
                (helper (cdr list-of-opponent-cells))))
    (helper (get-cell-list (other-player player) board)))

; Gets the largest user owned cell NUMBER
   (define (largest-user-cell board player)
    (define (helper my-cells)
        (if (= (list-ref (car my-cells) 5) (largest-user-cell-value board player))
            (list-ref (car my-cells) 0)
            (helper (cdr my-cells))))
    (helper (get-cell-list player board)))

    ;;Gets the largest opponent cell NUMBER
(define (largest-opponent-cell board player)
    (define (helper list-of-opponent-cells)
        (if (= (list-ref (car list-of-opponent-cells) 5) (largest-opponent-cell-value board player))
            (list-ref (car list-of-opponent-cells) 0)
            (helper (cdr list-of-opponent-cells))))
(helper (get-cell-list (other-player player) board)))

 ;;;PROCEDURE THAT MAKES THE LARGEST USER CONTROLLED CELL ATTACK THE SMALLEST P2 CONTROLLED CELL
    (define (attack-high-low player board)
     (list (make-move (largest-user-cell board player)
                      (smallest-opponent-cell board player))))

;;;A MULTITUDE OF ATTACKS THAT GOES FROM BEST CASE TO WORST CASE IN TERMS OF MOVING.
    (define (attack-skirmish player board) 
        (if (> (smallest-user-cell-value board player) (largest-opponent-cell-value board player)) ;smallest user cell attacks largest opponent if plausible
            (list (make-move (smallest-user-cell board player)
                             (largest-opponent-cell board player)))               
        (if (and (>  (/ (largest-user-cell-value board player) 2) (+ (largest-opponent-cell-value board player) 6))  (<= (/ (distance (largest-user-cell board player) (largest-opponent-cell board player) board) *MOVE-SPEED*) 2)) ; largest user cell attacks largest opponent cell if plausible, w/ distance
            (list (make-move (largest-user-cell board player)
                             (largest-opponent-cell board player)))
        (if (and (>  (/ (largest-user-cell-value board player) 2) (largest-opponent-cell-value board player))  (<= (/ (distance (largest-user-cell board player) (largest-opponent-cell board player) board) *MOVE-SPEED*) 1)) ; same as above, w/ distsance
            (list (make-move (largest-user-cell board player)
                             (largest-opponent-cell board player)))
        (if (>  (/ (largest-user-cell-value board player) 2) (largest-opponent-cell-value board player)) ;same as above, but without taking distance into account
            (list (make-move (largest-user-cell board player)
                             (largest-opponent-cell board player)))
            (attack-high-low player board)))))) 

  

   ;; Gets the first player owned cell on any given board, helpful for the start of the game
    (define (get-first-player-owned-cell board player) ; returns the first player owned cell on any board.
        (list-ref (car (get-cell-list player board)) 0))

   ;;Gets the second player owned cell on any given board
    (define (get-second-player-owned-cell board player)
    (if (<= (length (get-cell-list player board)) 1)
       '()    
        (list-ref (cadr (get-cell-list player board)) 0)))

   ;;Gets the third player owned cell on any given board
        (define (get-third-player-owned-cell board player)
    (if (<= (length (get-cell-list player board)) 1)
       '()    
        (list-ref (caddr (get-cell-list player board)) 0)))


   ;; Finds the minumum distance between the FIRST player controlled cell and all of the neutral cells on any board
    (define (mindistance-1-N board player)
        (define (helper neutral-cells)
            (if (null? neutral-cells)
                201
                (min (distance (get-first-player-owned-cell board player) (list-ref (car neutral-cells) 0) board)
                     (helper (cdr neutral-cells)))))
    (helper (get-cell-list 'n board)))

   ;; Finds the minimum distance between the SECOND player controlled cell and all of the neutral cells on any board
    (define (mindistance-2-N board player)
        (define (helper neutral-cells)
            (if (null? (get-second-player-owned-cell board player))
               '()
            (if (null? neutral-cells)
                201
                (min (distance (get-second-player-owned-cell board player) (list-ref (car neutral-cells) 0) board)
                     (helper (cdr neutral-cells))))))
   (helper (get-cell-list 'n board)))
  
   ;; Finds the minimum distance between the THIRD player controlled cell and all of the neutral cells on any board
    (define (mindistance-3-N board player)
        (define (helper neutral-cells)
            (if (null? (get-third-player-owned-cell board player))
               '()
            (if (null? neutral-cells)
                201
                (min (distance (get-third-player-owned-cell board player) (list-ref (car neutral-cells) 0) board)
                     (helper (cdr neutral-cells))))))
   (helper (get-cell-list 'n board)))
   
   ;; Makes a list of the FIRST player controlled cell and the closest neutral cell
   (define (owners-of-mindistance-1-N board player)
        (define (helper n neutral-cells)
            (if (= (mindistance-1-N board player) (distance (get-first-player-owned-cell board player) n board))
                   (list (get-first-player-owned-cell board player) n)
                   (helper (+ n 1) (get-cell-list 'n board))))
                   (helper 1 (get-cell-list 'n board)))

  ;; Makes a list of the SECOND player controlled cell and the closest neutral cell
    (define (owners-of-mindistance-2-N board player)
        (define (helper n neutral-cells)
            (if (null? (mindistance-2-N board player))
               '()
            (if (= (mindistance-2-N board player) (distance (get-second-player-owned-cell board player) n board))
                   (list (get-second-player-owned-cell board player) n)
                   (helper (+ n 1) (get-cell-list 'n board)))))
                   (helper 1 (get-cell-list 'n board)))

    ;; Makes a list of the THIRD player controlled cell and the closest neutral cell
     (define (owners-of-mindistance-3-N board player) 
        (define (helper n neutral-cells)
            (if (null? (mindistance-3-N board player))
               '()
            (if (= (mindistance-3-N board player) (distance (get-third-player-owned-cell board player) n board))
                   (list (get-third-player-owned-cell board player) n)
                   (helper (+ n 1) (get-cell-list 'n board)))))
                   (helper 1 (get-cell-list 'n board)))

   
   
    ;;Move to the Closest Neutral Cell - First Turn only                                  
    (define (move-to-neutral player board) ; procedure that moves the first (and only) player controlled cell to the closest neutral cell.
      (let ((my-cells (get-cell-list player board))
	    (neutral-cells (get-cell-list 'N board)))
      (if (<= *CURRENT-TURN* 2)
	(list (make-move (car  (owners-of-mindistance-1-N board player)) ; the player's first (and only) cell
			 (cadr (owners-of-mindistance-1-N board player)))) ;to THE CLOSEST neutral cell from that player controlled cell.
      (if (and (= *CURRENT-TURN* 3) (not (null? (get-second-player-owned-cell board player))))
        (list (make-move (get-first-player-owned-cell board player)
                         (car (random-element neutral-cells)))     
              (make-move (car  (owners-of-mindistance-2-N board player))
                         (cadr (owners-of-mindistance-2-N board player))))
    ;  (if (and (= *CURRENT-TURN* 4) (not (null? (get-third-player-owned-cell board player))) (not (null? (get-third-player-owned-cell board player))))
     ;   (list (make-move (get-first-player-owned-cell board player)
      ;                   (car  (random-element neutral-cells)))     
       ;       (make-move (get-second-player-owned-cell board player)
        ;                 (car  (random-element neutral-cells)))
         ;     (make-move (car  (owners-of-mindistance-3-N board player))
          ;               (cadr (owners-of-mindistance-3-N board player))))
    
        (list (make-move (car (random-element my-cells)) ; If all else fails, make a random move
			 (car (random-element neutral-cells)))))))) 
			 
			  
	
    ;;====================;;
    ;; Get-Move Procedure ;;
    ;;===============================================================;;
    ;; This is the procedure called by dots++.scm to get your move.  ;;
    ;; Returns a line-position object.
    ;;===============================================================;;

	;; Main procedure
    (define (get-move player queue board)
      (if (not (null? (get-cell-list 'N board))) ; if the cell list of neutral cells is not null.
	  (move-to-neutral player board) ; then move a single player controlled cell to THE CLOSEST neutral cell, then move randomly until all neutral cells are gone.
	  (attack-skirmish player board))) ; otherwise, if the cell list of neutral cells IS null, do attack-skirmish
	


    ;; Return get-move procedure
    get-move

    )) ;; End of player-procedure
    

