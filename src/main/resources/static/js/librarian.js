;var library = ( function function_name(argument) {

	return new Library();

	function Library() {
		
		var api = {
			BOOKS_ALL: "/books/all",
			BOOKS_BUSY: "/books/busy",
			OPT_REG: "/options/register",
			OPT_UNREG: "/unregister",
			OPT_HISTORY: "/history"
		}

		this.getAllBooks = function function_name() {
			$.ajax({
				url: '/books/size',
				type: 'POST',
			})
			.done(function(response) {

				var pages = Math.ceil(response.data/10);
				$.ajax({
					url: '/books/all',
					type: 'POST',
					data: "from="+1+"&size="+ 10
				})
				.done(function(obj) {
					document.getElementById("content").innerHTML = 
					Mustache.render(getTemplate("books_table"), obj);
				});

				$(function () {
					var obj = $('#pagination').twbsPagination({
						totalPages: pages,
						visiblePages: 5,
						onPageClick: function (event, page) {
							$.ajax({
								url: '/books/all',
								type: 'POST',
								data: "from="+((page-1)*10+1)+"&size="+ 10
							})
							.done(function(obj) {

								document.getElementById("content").innerHTML = 
								Mustache.render(getTemplate("books_table"), obj);
							});
						}
					});
					console.info(obj.data());
				});

				console.log("success");
			})
			.fail(function() {	
				console.log("error");
			})
			.always(function() {
				console.log("complete");
			});
			
		}
		this.gerBusyBooks = function function_name() {
			// body...
		}
		this.issuingBooks = function function_name() {
			// body...
		}
		this.receiptBooks = function function_name() {
    	// body...
    }
    this.history = function function_name() {
    	// body...
    }

    function getTemplate(temp_id) {
    	var elem = document.getElementById(temp_id);
    	return elem.innerHTML;
    }
  }
})();